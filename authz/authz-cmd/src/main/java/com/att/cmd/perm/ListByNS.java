/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.perm;

import com.att.aft.dme2.internal.jetty.http.HttpMethods;
import com.att.cadi.CadiException;
import com.att.cadi.LocatorException;
import com.att.cadi.client.Future;
import com.att.cadi.client.Rcli;
import com.att.cmd.Cmd;
import com.att.cmd.Param;
import com.att.inno.env.APIException;

import aaf.v2_0.Perms;

/**
 * Return Perms by NS
 * 
 *
 */
public class ListByNS extends Cmd {
	private static final String HEADER = "List Perms by NS ";
	
	public ListByNS(List parent) {
		super(parent,"ns", 
				new Param("name",true)); 
	}

	public int _exec( int idx, final String ... args) throws CadiException, APIException, LocatorException {
		final String ns=args[idx];

		return same(((List)parent).new ListPerms() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				Future<Perms> fp = client.read(
						"/authz/perms/ns/"+ns, 
						getDF(Perms.class)
						);
				return list(fp,client, HEADER, ns);
			}
		});
	}
	
	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,HEADER);
		api(sb,indent,HttpMethods.GET,"authz/perms/ns/<ns>",Perms.class,true);
	}


}
