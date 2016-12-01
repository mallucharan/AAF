/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.service.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;

import org.junit.Test;

import com.att.cadi.CadiException;
import com.att.cadi.client.Future;
import com.att.cadi.client.Rcli;
import com.att.cadi.client.Retryable;
import com.att.cadi.config.Config;
import com.att.inno.env.APIException;
import com.att.rosetta.InJson;
import com.att.rosetta.OutJson;
import com.att.rosetta.ParseException;

import aaf.v2_0.Api;

public class APITest extends AbsServiceTest {


	@Test
	public void test() throws Exception {
		
		hman.best(basicAuthSS, 
			new Retryable<Api>() {
				@Override
				public Api code(Rcli<?> cli) throws APIException, CadiException {
					Future<String> ft = cli.read("/api","text/json");  
					if(ft.get(10000)) {
						StringWriter sw = new StringWriter(2048);
						try {
							new OutJson().extract(new StringReader(ft.body()),sw,new InJson(),true);
						} catch (IOException | ParseException e) {
							throw new APIException(e);
						}
						System.out.println("//////  API  /////\n"+sw);
					} else {
						error(ft);
					}
					return null;
				}
			}
		);
	}
	
	@Test
	public void testExample() throws Exception {
		hman.best(basicAuthSS, 
			new Retryable<Api>() {
				@Override
				public Api code(Rcli<?> cli) throws APIException, CadiException {
					try {
						Future<String> ft = cli.read("/api/example/"+URLEncoder.encode(
								"application/Perms+json;charset=utf-8;version=2.0;application/json/version=2.0,*/*",Config.UTF_8)
								,"text/plain");  
						if(ft.get(10000)) {
							System.out.println("//////  API  Example  /////\n");
							new OutJson().extract(new StringReader(ft.body()),System.out,new InJson(),true);
						} else {
							error(ft);
						}
						return null;
					} catch (IOException | ParseException e) {
						throw new APIException(e);
					}
				}
			}
		);
	}
	
	private void error(Future<?> ft) throws APIException {
		System.out.println(ft.body());
//		DataFactory<Error> df = env.newDataFactory(Error.class);
//		Error err = df.newData().load(ft.body()).in(Data.TYPE.JSON).asObject();
//		System.out.println(err.getMessageId() + ": " + err.getText());
		System.out.flush();
	}


}
