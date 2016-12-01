/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.role;

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

import aaf.v2_0.History;

/**
 *
 */
public class ListActivity extends Cmd {
	private static final String HEADER = "List Activity of Role";

	public ListActivity(List parent) {
		super(parent,"activity", 
				new Param("name",true));
	}

	@Override
	public int _exec(int _idx, final String ... args) throws CadiException, APIException, LocatorException {
	        int idx = _idx;
		final String role = args[idx++];
		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				Future<History> fp = client.read(
						"/authz/hist/role/"+role, 
						getDF(History.class)
						);
				if(fp.get(AAFcli.timeout())) {
					activity(fp.value,HEADER + " [ " + role + " ]");
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
		api(sb,indent,HttpMethods.GET,"authz/hist/role/<role>",History.class,true);
	}
}
