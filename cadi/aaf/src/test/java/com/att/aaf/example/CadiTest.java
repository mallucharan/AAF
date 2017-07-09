/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.aaf.example;

import java.net.HttpURLConnection;
import java.net.URI;

import com.att.cadi.Access;
import com.att.cadi.PropAccess;
import com.att.cadi.client.Future;
import com.att.cadi.config.SecurityInfoC;
import com.att.cadi.http.HClient;
import com.att.cadi.http.HX509SS;

public class CadiTest {
	public static void main(String args[]) {
		Access access = new PropAccess();
		try {
			SecurityInfoC<HttpURLConnection> si = new SecurityInfoC<HttpURLConnection>(access);
			HClient hclient = new HClient(
				new HX509SS(si),
				new URI("https://mithrilcsp.sbc.com:8085"),3000);
			hclient.setMethod("OPTIONS");
			hclient.setPathInfo("/gui/cadi/log/toggle/INFO");
			hclient.send();
			Future<String> future = hclient.futureReadString();
			if(future.get(5000)) {
				System.out.println(future.value);
			} else {
				System.out.printf("Error: %d-%s", future.code(),future.body());
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
