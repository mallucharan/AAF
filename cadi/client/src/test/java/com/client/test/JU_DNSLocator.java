/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.client.test;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.junit.AfterClass;
import org.junit.Test;

import com.att.cadi.Locator.Item;
import com.att.cadi.dnsloc.DNSLocator;
import com.client.test.TestAccess;

public class JU_DNSLocator {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		
		DNSLocator dl = new DNSLocator(new TestAccess(), "https", "aaf.it.att.com","8150-8152");
		try {
			Item item = dl.best();
			URI uri = dl.get(item);
			URL url = uri.toURL();
			URLConnection conn = url.openConnection();
			conn.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
