/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.user;

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

import aaf.v2_0.History;

/**
 *
 */
public class ListActivity extends Cmd {
	private static final String HEADER = "List Activity of User";

	public ListActivity(List parent) {
		super(parent,"activity", 
				new Param("user",true));
	}

	@Override
	public int _exec(int _idx, final String ... args) throws CadiException, APIException, LocatorException {
	        int idx = _idx;
		String user = args[idx++];
		String realm = getOrgRealm();
		final String fullUser = (user.indexOf('@') < 0 && realm != null)?user + '@' + realm:user;
		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
		
				Future<History> fp = client.read(
						"/authz/hist/user/"+fullUser, 
						getDF(History.class)
						);
				if(fp.get(AAFcli.timeout())) {
					activity(fp.value,HEADER + " [ " + fullUser + " ]");
				} else {
					error(fp);
				}
				return fp.code();
			}
		});
	}
	

	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,HEADER);
		api(sb,indent,HttpMethods.GET,"authz/hist/user/<user>",History.class,true);
	}

}
