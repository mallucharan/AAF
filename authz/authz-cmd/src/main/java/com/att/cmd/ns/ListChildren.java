/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.ns;

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

import aaf.v2_0.Nss;
import aaf.v2_0.Nss.Ns;

/**
 * p
 *
 */
public class ListChildren extends Cmd {
	private static final String HEADER="List Child Namespaces";
	
	public ListChildren(List parent) {
		super(parent,"children", 
				new Param("ns",true));
	}

	@Override
	public int _exec(int _idx, final String ... args) throws CadiException, APIException, LocatorException {
	        int idx = _idx;
		final String ns=args[idx++];
		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				Future<Nss> fn = client.read("/authz/nss/children/"+ns,getDF(Nss.class));
				if(fn.get(AAFcli.timeout())) {
					parent.reportHead(HEADER);
					for(Ns ns : fn.value.getNs()) {
						pw().format(List.kformat, ns.getName());
					}
				} else if(fn.code()==404) {
					((List)parent).report(null,HEADER,ns);
					return 200;
				} else {	
					error(fn);
				}
				return fn.code();
			}
		});
	}

	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,HEADER);
		api(sb,indent,HttpMethods.GET,"authz/nss/children/<ns>",Nss.class,true);
	}

}
