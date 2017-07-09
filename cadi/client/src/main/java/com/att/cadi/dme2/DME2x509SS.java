/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.dme2;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;

import com.att.aft.dme2.api.DME2Client;
import com.att.cadi.CadiException;
import com.att.cadi.SecuritySetter;
import com.att.cadi.config.Config;
import com.att.cadi.config.SecurityInfoC;
import com.att.inno.env.APIException;


public class DME2x509SS implements SecuritySetter<DME2Client> {
	private String alias;

	public DME2x509SS(final String sendAlias, SecurityInfoC<DME2Client> si) throws APIException, IOException, CertificateEncodingException {
		if((alias=sendAlias) == null) {
			if(si.default_alias == null) {
				throw new APIException("JKS Alias is required to use X509SS Security.  Use " + Config.CADI_ALIAS +" to set default alias");
			} else {
				alias = si.default_alias;
			}
		}
	}

	@Override
	public void setSecurity(DME2Client dme2) throws CadiException {
		// DME2Client has to have properties set before creation to work.
	}
	
	/* (non-Javadoc)
	 * @see com.att.cadi.SecuritySetter#getID()
	 */
	@Override
	public String getID() {
		return alias;
	}

	@Override
	public int setLastResponse(int respCode) {
		return 0;
	}

}
