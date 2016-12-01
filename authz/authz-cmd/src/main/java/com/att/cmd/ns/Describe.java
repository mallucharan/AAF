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

import aaf.v2_0.NsRequest;

public class Describe extends Cmd {
	private static final String NS_PATH = "/authz/ns";
	public Describe(NS parent) {
		super(parent,"describe", 
				new Param("name",true),
				new Param("description",true)); 
	}

	@Override
	public int _exec(final int index, final String ... args) throws CadiException, APIException, LocatorException {
		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				int idx = index;
				String name = args[idx++];
				StringBuilder desc = new StringBuilder();
				while (idx < args.length) {
					desc.append(args[idx++] + ' ');
				}
		
				NsRequest nsr = new NsRequest();
				nsr.setName(name);
				nsr.setDescription(desc.toString());
		
				// Set Start/End commands
				setStartEnd(nsr);
				
				Future<NsRequest> fn = null;
				int rv;

				fn = client.update(
					NS_PATH,
					getDF(NsRequest.class),
					nsr
					);

				if(fn.get(AAFcli.timeout())) {
					rv=fn.code();
					pw().println("Description added to Namespace");
				} else {
					if((rv=fn.code())==202) {
						pw().print("Adding description");
						pw().println(" Accepted, but requires Approvals before actualizing");
					} else {
						error(fn);
					}
				}
				return rv;
			}
		});
	}

	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,"Add a description to a namespace");
		api(sb,indent,HttpMethods.PUT,"authz/ns",NsRequest.class,true);
	}
}