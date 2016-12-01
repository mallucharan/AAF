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
 * 
 *
 */
public class ListByName extends Cmd {
	private static final String HEADER = "List Child Permissions";
	
	public ListByName(List parent) {
		super(parent,"name", 
				new Param("root perm name",true)); 
	}

	public int _exec(final int index, final String ... args) throws CadiException, APIException, LocatorException {
		return same(((List)parent).new ListPerms() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				String parentPerm=args[index];
				
				Future<Perms> fp = client.read(
						"/authz/perms/"+parentPerm, 
						getDF(Perms.class) 
						);
				return list(fp,client,HEADER,parentPerm);
			}
		});
	}

	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,HEADER);
		api(sb,indent,HttpMethods.GET,"authz/perms/<parent type>",Perms.class,true);
	}

}
