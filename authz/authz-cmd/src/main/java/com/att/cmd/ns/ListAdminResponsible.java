/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.ns;

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

import aaf.v2_0.Nss;

public class ListAdminResponsible extends Cmd {
	private static final String HEADER="List Namespaces with ";
	private final static String[] options = {"admin","responsible"};
	
	public ListAdminResponsible(List parent) {
		super(parent,null, 
				new Param(optionsToString(options),true),
				new Param("user",true)); 
	}

	@Override
	protected int _exec(final int index, final String... args) throws CadiException, APIException, LocatorException {

		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				int idx = index;
				String title = args[idx++];
				String user = args[idx++];
				if (user.indexOf('@') < 0 && getOrgRealm() != null) user += '@' + getOrgRealm();
				
				Future<Nss> fn = client.read("/authz/nss/"+title+"/"+user,getDF(Nss.class));
				if(fn.get(AAFcli.timeout())) {
					((List)parent).reportName(fn,HEADER + title + " privileges for ",user);
				} else if(fn.code()==404) {
					((List)parent).report(null,HEADER + title + " privileges for ",user);
					return 200;
				} else {	
					error(fn);
				}
				return fn.code();
			}
		});
	}
	
	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,HEADER + "admin or responsible priveleges for user");
		api(sb,indent,HttpMethods.GET,"authz/nss/<admin|responsible>/<user>",Nss.class,true);
	}
}
