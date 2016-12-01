/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.role;

import com.att.aft.dme2.internal.jetty.http.HttpMethods;
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

public class Describe extends Cmd {
	private static final String ROLE_PATH = "/authz/role";
	public Describe(Role parent) {
		super(parent,"describe", 
				new Param("name",true),
				new Param("description",true)); 
	}

	@Override
	public int _exec(final int index, final String ... args) throws CadiException, APIException, LocatorException {
		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				int idx = index;
				String role = args[idx++];
				StringBuilder desc = new StringBuilder();
				while (idx < args.length) {
					desc.append(args[idx++] + ' ');
				}
		
				RoleRequest rr = new RoleRequest();
				rr.setName(role);
				rr.setDescription(desc.toString());
		
				// Set Start/End commands
				setStartEnd(rr);
				
				Future<RoleRequest> fp = null;
				int rv;

				fp = client.update(
					ROLE_PATH,
					getDF(RoleRequest.class),
					rr
					);

				if(fp.get(AAFcli.timeout())) {
					rv=fp.code();
					pw().println("Description added to role");
				} else {
					if((rv=fp.code())==202) {
						pw().print("Adding description");
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
		detailLine(sb,indent,"Add a description to a role");
		api(sb,indent,HttpMethods.PUT,"authz/role",RoleRequest.class,true);
	}
}
