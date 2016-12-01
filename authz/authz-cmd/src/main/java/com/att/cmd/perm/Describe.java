/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.perm;

import com.att.cadi.CadiException;
import com.att.cadi.LocatorException;
import com.att.cadi.client.Future;
import com.att.cadi.client.Rcli;
import com.att.cadi.client.Retryable;
import com.att.cmd.AAFcli;
import com.att.cmd.Cmd;
import com.att.cmd.Param;
import com.att.cssa.rserv.HttpMethods;
import com.att.inno.env.APIException;

import aaf.v2_0.PermRequest;

public class Describe extends Cmd {
	private static final String PERM_PATH = "/authz/perm";
	public Describe(Perm parent) {
		super(parent,"describe", 
				new Param("type",true),
				new Param("instance", true),
				new Param("action", true),
				new Param("description",true)); 
	}

	@Override
	public int _exec(final int index, final String ... args) throws CadiException, APIException, LocatorException {
		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				int idx = index;
				String type = args[idx++];
				String instance = args[idx++];
				String action = args[idx++];
				StringBuilder desc = new StringBuilder();
				while (idx < args.length) {
					desc.append(args[idx++] + ' ');
				}
		
				PermRequest pr = new PermRequest();
				pr.setType(type);
				pr.setInstance(instance);
				pr.setAction(action);
				pr.setDescription(desc.toString());
		
				// Set Start/End commands
				setStartEnd(pr);
				
				Future<PermRequest> fp = null;
				int rv;

				fp = client.update(
					PERM_PATH,
					getDF(PermRequest.class),
					pr
					);

				if(fp.get(AAFcli.timeout())) {
					rv=fp.code();
					pw().println("Description added to Permission");
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
		detailLine(sb,indent,"Add a description to a permission");
		api(sb,indent,HttpMethods.PUT,"authz/perm",PermRequest.class,true);
	}
}
