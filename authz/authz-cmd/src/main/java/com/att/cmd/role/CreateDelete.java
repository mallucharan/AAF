/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.role;

import com.att.aft.dme2.internal.jetty.http.HttpMethods;
import com.att.aft.dme2.internal.jetty.http.HttpStatus;
import com.att.cadi.CadiException;
import com.att.cadi.LocatorException;
import com.att.cadi.client.Future;
import com.att.cadi.client.Rcli;
import com.att.cadi.client.Retryable;
import com.att.cmd.AAFcli;
import com.att.cmd.Cmd;
import com.att.cmd.Param;
import com.att.inno.env.APIException;

import aaf.v2_0.RoleRequest;

/**
 * 
 *
 */
public class CreateDelete extends Cmd {
	private static final String ROLE_PATH = "/authz/role";
	private final static String[] options = {"create","delete"};
	public CreateDelete(Role parent) {
		super(parent,null, 
				new Param(optionsToString(options),true),
				new Param("name",true)); 
	}

	@Override
	public int _exec(final int index, final String ... args) throws CadiException, APIException, LocatorException {
		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				int idx = index;
				String action = args[idx++];
				int option = whichOption(options, action);
		
				RoleRequest rr = new RoleRequest();
				rr.setName(args[idx++]);
		
				// Set Start/End commands
				setStartEnd(rr);
				
				Future<RoleRequest> fp = null;
				String verb = null;
				int rv;
				switch(option) {
					case 0:
						fp = client.create(
							ROLE_PATH,
							getDF(RoleRequest.class),
							rr
							);
						verb = "Create";
						break;
					case 1:
						// Send "Force" if set
						setQueryParamsOn(client);
						fp = client.delete(
								ROLE_PATH, // +args[idx++], 
								getDF(RoleRequest.class),
								rr
								);
						verb = "Delete";
						break;
					default: // note, if not an option, whichOption throws Exception
						break;
						
				}
				boolean rolesSupplied = (args.length>idx);
				if(fp.get(AAFcli.timeout())) {
					rv=fp.code();
					pw().print(verb);
					pw().println("d Role");
					if(rolesSupplied) {
						for(;args.length>idx;++idx ) {
							try {
								if(201!=(rv=((Role)parent)._exec(0,new String[] {"user","add",rr.getName(),args[idx]}))) {
									rv = HttpStatus.PARTIAL_CONTENT_206;
								}
							} catch (LocatorException e) {
								throw new CadiException(e);
							}
						}
					}
				} else {
					if((rv=fp.code())==202) {
						pw().print("Role ");
						pw().print(verb);
						pw().println(" Accepted, but requires Approvals before actualizing");
					} else {
						error(fp);
					}
				}
				return rv;
			}
		});
	}

	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,"Create OR Delete a Role");
		detailLine(sb,indent+2,"name - Name of Role to create");
		api(sb,indent,HttpMethods.POST,"authz/role",RoleRequest.class,true);
		api(sb,indent,HttpMethods.DELETE,"authz/role",RoleRequest.class,false);
	}

}
