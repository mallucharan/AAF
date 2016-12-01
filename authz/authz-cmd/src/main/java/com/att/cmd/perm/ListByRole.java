/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.perm;

import com.att.cadi.CadiException;
import com.att.cadi.LocatorException;
import com.att.cadi.client.Future;
import com.att.cadi.client.Rcli;
import com.att.cmd.Cmd;
import com.att.cmd.Param;
import com.att.cssa.rserv.HttpMethods;
import com.att.inno.env.APIException;

import aaf.v2_0.Perms;

/**
 * Return Perms by Role
 * 
 *
 */
public class ListByRole extends Cmd {
	private static final String HEADER = "List Perms by Role ";
	
	public ListByRole(List parent) {
		super(parent,"role", 
				new Param("name",true)); 
	}

	public int _exec(final int idx, final String ... args) throws CadiException, APIException, LocatorException {
		final String role=args[idx];

		return same(((List)parent).new ListPerms() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {

				Future<Perms> fp = client.read(
						"/authz/perms/role/"+role, 
						getDF(Perms.class)
						);
				return list(fp,client, HEADER, role);
			}
		});
	}
	
	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,HEADER);
		api(sb,indent,HttpMethods.GET,"authz/perms/role/<role>",Perms.class,true);
	}


}
