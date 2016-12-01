/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.client;

import com.att.cadi.CadiException;
import com.att.inno.env.APIException;
import com.att.rosetta.env.RosettaDF;

public class Delete<T> extends AAFClient.Call<T> {
	public Delete(AAFClient ac, RosettaDF<T> df) {
		super(ac,df);
	}

	@SuppressWarnings("unchecked")
	public Result<T> delete(final String pathInfo, final T t) throws Exception {
		if(t==null) {
			return (Result<T>)delete(pathInfo);
		}
		return client.hman.best(client.ss, 
			 new Retryable<Result<T>>() {
				@Override
				public Result<T> code(Rcli<?> client) throws APIException, CadiException {
					Future<T> ft = client.delete(pathInfo,df,t); 
					if(ft.get(client.readTimeout)) {
						return Result.ok(ft.code(),ft.value);
					} else {
						return Result.err(ft.code(),ft.body());
					}
				}
			});
	}

	public Result<Void> delete(final String pathInfo) throws Exception {
		return client.hman.best(client.ss, 
			 new Retryable<Result<Void>>() {
				@Override
				public Result<Void> code(Rcli<?> client) throws APIException, CadiException {
					Future<Void> ft = client.delete(pathInfo,VOID_CONTENT_TYPE); 
					if(ft.get(client.readTimeout)) {
						return Result.ok(ft.code(),ft.value);
					} else {
						return Result.err(ft.code(),ft.body());
					}
				}
			});
	}



}