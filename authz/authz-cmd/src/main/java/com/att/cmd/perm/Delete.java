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

import aaf.v2_0.PermRequest;

/**
 *
 */
public class Delete extends Cmd {
	public Delete(Perm parent) {
		super(parent,"delete", 
				new Param("type",true), 
				new Param("instance",true),
				new Param("action", true));
	}

	@Override
	public int _exec(final int index, final String ... args) throws CadiException, APIException, LocatorException {
		return same(new Retryable<Integer>() {
			@Override
			public Integer code(Rcli<?> client) throws CadiException, APIException {
				int idx = index;
				// Object Style Delete
				PermRequest pk = new PermRequest();
				pk.setType(args[idx++]);
				pk.setInstance(args[idx++]);
				pk.setAction(args[idx++]);
		
				// Set "Force" if set
				setQueryParamsOn(client);
				Future<PermRequest> fp = client.delete(
						"/authz/perm", 
						getDF(PermRequest.class),
						pk);
				if(fp.get(AAFcli.timeout())) {
					pw().println("Deleted Permission");
				} else {
					if(fp.code()==202) {
						pw().println("Permission Deletion Accepted, but requires Approvals before actualizing");
					} else {
						error(fp);
					}
				}
				return fp.code();
			}
		});
	}

	@Override
	public void detailedHelp(int indent, StringBuilder sb) {
		detailLine(sb,indent,"Delete a Permission with type,instance and action");
		detailLine(sb,indent+4,"see Create for definitions");
		api(sb,indent,HttpMethods.DELETE,"authz/perm",PermRequest.class,true);
	}

}
