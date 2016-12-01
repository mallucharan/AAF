/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.client;

import com.att.cadi.CadiException;
import com.att.inno.env.APIException;
import com.att.rosetta.env.RosettaDF;

public class Get<T> extends AAFClient.Call<T> {
	public Get(AAFClient ac, RosettaDF<T> df) {
		super(ac,df);
	}
	
	public Result<T> read(final String pathInfo) throws Exception {
		return client.hman.best(client.ss, 
			 new Retryable<Result<T>>() {
				@Override
				public Result<T> code(Rcli<?> client) throws APIException, CadiException {
					Future<T> ft = client.read(pathInfo,df); 
					if(ft.get(client.readTimeout)) {
						return Result.ok(ft.code(),ft.value);
					} else {
						return Result.err(ft.code(),ft.body());
					}
				}
			});
	}
}