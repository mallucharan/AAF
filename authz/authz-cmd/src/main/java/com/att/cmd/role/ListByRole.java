/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.role;

import com.att.aft.dme2.internal.jetty.http.HttpMethods;
import com.att.cadi.CadiException;
import com.att.cadi.LocatorException;
import com.att.cadi.client.Future;
import com.att.cadi.client.Rcli;
import com.att.cmd.Cmd;
import com.att.cmd.Param;
import com.att.inno.env.APIException;

import aaf.v2_0.Roles;

/**
 * 
 *
 */
public class ListByRole extends Cmd {
	private static final String HEADER="List Roles for Role";
	
	public ListByRole(List parent) {
		super(parent,"role", 
				new Param("role",true)); 
	}

	@Override
	public int _exec(final int idx, final String ... args) throws CadiException, APIException, LocatorException {
		return same(((List)parent).new ListRoles() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				String role=args[idx];	
				Future<Roles> fp = client.read(
						"/authz/roles/"+role, 
						getDF(Roles.class) 
						);
				return list(fp,client,HEADER+"["+role+"]");
			}
		});
	}
	
	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,HEADER);
		api(sb,indent,HttpMethods.GET,"authz/roles/<role>",Roles.class,true);
	}

}
