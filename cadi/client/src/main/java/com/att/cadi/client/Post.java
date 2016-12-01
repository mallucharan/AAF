/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.client;

import com.att.cadi.CadiException;
import com.att.cadi.LocatorException;
import com.att.inno.env.APIException;
import com.att.rosetta.env.RosettaDF;

public class Post<T> extends AAFClient.Call<T> {
	public Post(AAFClient ac, RosettaDF<T> df) {
		super(ac,df);
	}
	
	public Result<T> create(final String pathInfo, final T t) throws APIException, CadiException, LocatorException {
		return client.hman.best(client.ss, 
			 new Retryable<Result<T>>() {
				@Override
				public Result<T> code(Rcli<?> client) throws  APIException, CadiException {
					Future<T> ft = client.create(pathInfo,df,t); 
					if(ft.get(client.readTimeout)) {
						return Result.ok(ft.code(),ft.value);
					} else {
						return Result.err(ft.code(),ft.body());
					}
				}
			});
	}
}