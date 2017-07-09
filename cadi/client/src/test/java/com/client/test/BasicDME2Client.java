/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.client.test;

import java.net.URI;
import java.util.Properties;

import com.att.aft.dme2.api.DME2Client;
import com.att.aft.dme2.api.DME2Manager;

public class BasicDME2Client {
	public static void main(String[] args) {
		try {
			Properties props = System.getProperties();
			
			DME2Manager dm = new DME2Manager("DME2Manager TestBasicDME2Client",props);
			URI uri = new URI(System.getProperty("aaf_url"));
			DME2Client client = new DME2Client(dm,uri,3000);

			System.out.println(props.getProperty("aaf_id"));
			client.setCredentials(props.getProperty("aaf_id"),props.getProperty("aaf_password"));
			
			String path = String.format("/authz/perms/user/%s@csp.att.com",args.length>0?args[0]:"xx9999");
			System.out.printf("Path: %s\n",path);
			client.addHeader("Accept", "application/Perms+json;q=1.0;charset=utf-8;version=2.0,application/json;q=1.0;version=2.0,*");
			client.setMethod("GET");
			client.setContext(path);
			client.setPayload("");// Note: Even on "GET", you need a String in DME2
			
			String o = client.sendAndWait(5000); // There are other Asynchronous call options, see DME2 Docs
			if(o==null) {
				System.out.println('[' + o + ']' + " (blank is good)");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
