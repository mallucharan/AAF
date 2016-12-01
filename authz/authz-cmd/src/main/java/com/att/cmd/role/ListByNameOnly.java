/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.role;

import com.att.cadi.CadiException;
import com.att.cadi.LocatorException;
import com.att.cadi.client.Future;
import com.att.cadi.client.Rcli;
import com.att.cmd.Cmd;
import com.att.cmd.Param;
import com.att.cssa.rserv.HttpMethods;
import com.att.inno.env.APIException;

import aaf.v2_0.Roles;

/**
 * Return Roles by NS
 * 
 *
 */
public class ListByNameOnly extends Cmd {
	private static final String HEADER = "List Roles by Name ";
	
	public ListByNameOnly(List parent) {
		super(parent,"name", 
				new Param("name",true)); 
	}

	@Override
	public int _exec( int idx, final String ... args) throws CadiException, APIException, LocatorException {
		final String name=args[idx];

		return same(((List)parent).new ListRoles() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				Future<Roles> fp = client.read(
						"/authz/roles/name/"+name, 
						getDF(Roles.class)
						);
				return list(fp,client, HEADER+"["+name+"]");
			}
		});
	}
	
	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,HEADER);
		api(sb,indent,HttpMethods.GET,"authz/roles/name/<name>",Roles.class,true);
	}


}
