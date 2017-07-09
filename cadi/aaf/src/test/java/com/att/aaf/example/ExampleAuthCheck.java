/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.aaf.example;

import com.att.cadi.PropAccess;
import com.att.cadi.aaf.v2_0.AAFAuthn;
import com.att.cadi.aaf.v2_0.AAFConHttp;
import com.att.cadi.locator.DNSLocator;

public class ExampleAuthCheck {
	public static void main(String args[]) {
		// Link or reuse to your Logging mechanism
		PropAccess myAccess = new PropAccess(); // 
		
		try {
			AAFConHttp acon = new AAFConHttp(myAccess, new DNSLocator(
					myAccess,"https","localhost","8100"));
			AAFAuthn<?> authn = acon.newAuthn();
			long start; 
			for (int i=0;i<10;++i) {
				start = System.nanoTime();
				String err = authn.validate("", "gritty");
				if(err!=null) System.err.println(err);
				else System.out.println("I'm ok");
				
				err = authn.validate("bogus", "gritty");
				if(err!=null) System.err.println(err + " (correct error)");
				else System.out.println("I'm ok");

				System.out.println((System.nanoTime()-start)/1000000f + " ms");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
