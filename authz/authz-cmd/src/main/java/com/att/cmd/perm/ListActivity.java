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

import aaf.v2_0.History;

/**
 *
 */
public class ListActivity extends Cmd {
	private static final String HEADER = "List Activity of Permission";
	
	public ListActivity(List parent) {
		super(parent,"activity", 
				new Param("type",true));
	}

	@Override
	public int _exec(final int index, final String ... args) throws CadiException, APIException, LocatorException {
		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				int idx = index;
				String type = args[idx++];
				Future<History> fp = client.read(
						"/authz/hist/perm/"+type, 
						getDF(History.class)
						);
				if(fp.get(AAFcli.timeout())) {
					activity(fp.value, HEADER + " [ " + type + " ]");
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
		api(sb,indent,HttpMethods.GET,"authz/hist/perm/<type>",History.class,true);
	}

}
