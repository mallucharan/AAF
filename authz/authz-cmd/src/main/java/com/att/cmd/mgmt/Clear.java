/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.mgmt;

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

/**
 * p
 *
 */
public class Clear extends Cmd {
	public Clear(Cache parent) {
		super(parent,"clear",
				new Param("name[,name]*",true));
	}

	@Override
	public int _exec(int _idx, String ... args) throws CadiException, APIException, LocatorException {
	        int idx = _idx;
		int rv=409;
		for(final String name : args[idx++].split(COMMA)) {
			rv = all(new Retryable<Integer>() {
				@Override
				public Integer code(Rcli<?> client) throws APIException, CadiException {
					int rv = 409;
					Future<Void> fp = client.delete(
							"/mgmt/cache/"+name, 
							Void.class
							);
					if(fp.get(AAFcli.timeout())) {
						pw().println("Cleared Cache for " + name + " on " + client);
						rv=200;
					} else {
						if(rv==409)rv = fp.code();
						error(fp);
					}
					return rv;
				}
			});
		}
		return rv;
	}

	@Override
	public void detailedHelp(int _indent, StringBuilder sb) {
	        int indent = _indent;
		detailLine(sb,indent,"Clear the cache for certain tables");
		indent+=2;
		detailLine(sb,indent,"name        - name of table or 'all'");
		detailLine(sb,indent+14,"Must have admin rights to 'com.att.aaf'");
		indent-=2;
		api(sb,indent,HttpMethods.DELETE,"mgmt/cache/:name",Void.class,true);
	}

}
