/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.dme2;

import java.io.IOException;
import java.security.Principal;

import com.att.aft.dme2.api.DME2Client;
import com.att.cadi.CadiException;
import com.att.cadi.client.AbsTransferSS;
import com.att.cadi.config.Config;
import com.att.cadi.config.SecurityInfo;

public class DME2TransferSS extends AbsTransferSS<DME2Client> {

	public DME2TransferSS(Principal principal, String app) throws IOException {
		super(principal, app);
	}

	public DME2TransferSS(Principal principal, String app, SecurityInfo<DME2Client> si) throws IOException {
		super(principal, app, si);
	}

	@Override
	public void setSecurity(DME2Client client) throws CadiException {
		if(value!=null) {
			if(defSS==null) throw new CadiException("Need App Credentials to send message");
			defSS.setSecurity(client);
			client.addHeader(Config.CADI_USER_CHAIN, value);
		}
	}
}
