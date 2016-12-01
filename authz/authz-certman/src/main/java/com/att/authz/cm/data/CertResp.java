/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cm.data;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.cert.X509Certificate;

import com.att.authz.cm.cert.CSRMeta;
import com.att.cadi.cm.CertException;
import com.att.cadi.cm.Factory;
import com.att.inno.env.Trans;

public class CertResp {
	public CertResp(Trans trans, X509Certificate x509, CSRMeta csrMeta, String[] notes) throws IOException, GeneralSecurityException, CertException {
		keyPair = csrMeta.keypair(trans);
		privateKey = Factory.toString(trans, keyPair.getPrivate());
		certString = Factory.toString(trans,x509);
		challenge=csrMeta.challenge();
		this.notes = notes;
	}
	private KeyPair keyPair;
	private String challenge;
	
	private String privateKey, certString;
	private String[] notes;
	
	
	public String asCertString() {
		return certString;
	}
	
	public String privateString() throws IOException {
		return privateKey;
	}
	
	public String challenge() {
		return challenge==null?"":challenge;
	}
	
	public String[] notes() {
		return notes;
	}
}
