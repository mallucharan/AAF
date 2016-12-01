/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd;

import java.io.IOException;

import com.att.aft.dme2.api.DME2Client;
import com.att.cadi.SecuritySetter;
import com.att.cadi.Symm;

public class BasicAuth implements SecuritySetter<DME2Client> {
	private String cred;
	private String user;
	
	public BasicAuth(String user, String pass) throws IOException {
		this.user = user;
		cred = "Basic " + Symm.base64.encode(user+':'+pass);
	}
	
	@Override
	public void setSecurity(DME2Client client) {
		client.addHeader("Authorization" , cred);
	}

	@Override
	public String getID() {
		return user;
	}

}
