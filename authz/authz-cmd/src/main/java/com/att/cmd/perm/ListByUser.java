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
public class ListByUser extends Cmd {
	private static final String HEADER = "List Permissions by User";
	public ListByUser(List parent) {
		super(parent,"user", 
				new Param("id",true)); 
	}

	public int _exec( int idx, final String ... args) throws CadiException, APIException, LocatorException {
		String user=args[idx];
		String realm = getOrgRealm();
		final String fullUser;
		if (user.indexOf('@') < 0 && realm != null) 
			fullUser = user + '@' + realm;
		else
			fullUser = user;
		
		return same(((List)parent).new ListPerms() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				Future<Perms> fp = client.read(
						"/authz/perms/user/"+fullUser, 
						getDF(Perms.class)
						);
				return list(fp, client, HEADER, fullUser);
			}
		});
	}
	
	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,HEADER);
		api(sb,indent,HttpMethods.GET,"authz/perms/user/<user id>",Perms.class,true);
	}


}
