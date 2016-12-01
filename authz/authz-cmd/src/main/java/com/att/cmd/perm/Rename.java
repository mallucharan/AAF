/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.perm;


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

import aaf.v2_0.PermRequest;

public class Rename extends Cmd {
	public Rename(Perm parent) {
		super(parent,"rename", 
				new Param("type",true), 
				new Param("instance",true),
				new Param("action", true),
				new Param("new type",true), 
				new Param("new instance",true),
				new Param("new action", true)
				);
	}
	
	@Override
	public int _exec(final int index, final String ... args) throws CadiException, APIException, LocatorException {
		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				int idx = index;
				String origType = args[idx++];
				String origInstance = args[idx++];
				String origAction = args[idx++];
				
				//Create new permission
				PermRequest pr = new PermRequest();
				pr.setType(args[idx++]);
				pr.setInstance(args[idx++]);
				pr.setAction(args[idx++]);
				
				// Set Start/End commands
				setStartEnd(pr);
				Future<PermRequest> fp = client.update(
						"/authz/perm/"+origType+"/"+origInstance+"/"+origAction,
						getDF(PermRequest.class),
						pr
						);
				int rv;
				if(fp.get(AAFcli.timeout())) {
					rv = fp.code();
					pw().println("Updated Permission");
				} else {
					rv = fp.code();
					if(rv==202) {
						pw().println("Permission Update Accepted, but requires Approvals before actualizing");
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
		detailLine(sb,indent,"Rename a Permission from:");
		detailLine(sb,indent+2,"<type> <instance> <action>");
		detailLine(sb,indent,"to:");
		detailLine(sb,indent+2,"<new type> <new instance> <new action>");
		sb.append('\n');
		detailLine(sb,indent,"Namespace must be the same in <type> and <new type>");
		detailLine(sb,indent+4,"see Create for definitions of type,instance and action");
		api(sb,indent,HttpMethods.PUT,"authz/perm/<type>/<instance>/<action>",PermRequest.class,true);
	}
}
