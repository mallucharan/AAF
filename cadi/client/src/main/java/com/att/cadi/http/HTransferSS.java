/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.Principal;

import javax.net.ssl.HttpsURLConnection;

import com.att.cadi.CadiException;
import com.att.cadi.client.AbsTransferSS;
import com.att.cadi.config.Config;
import com.att.cadi.config.SecurityInfoC;


public class HTransferSS extends AbsTransferSS<HttpURLConnection> {
	public HTransferSS(Principal principal, String app) throws IOException {
		super(principal, app);
	}
	
	public HTransferSS(Principal principal, String app, SecurityInfoC<HttpURLConnection> si) {
		super(principal, app, si);
	}

	@Override
	public void setSecurity(HttpURLConnection huc) throws CadiException {
		if(value!=null) {
				if(defSS==null) {
					throw new CadiException("Need App Credentials to send message");
				}
				defSS.setSecurity(huc);
				huc.addRequestProperty(Config.CADI_USER_CHAIN, value);
		}
		if(securityInfo!=null) {
			securityInfo.setSocketFactoryOn((HttpsURLConnection)huc);
		}
	}
	
	@Override
	public int setLastResponse(int respCode) {
		return 0;
	}

}
