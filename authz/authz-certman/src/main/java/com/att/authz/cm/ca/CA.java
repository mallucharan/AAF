/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cm.ca;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;

import com.att.authz.cm.cert.CSRMeta;
import com.att.authz.cm.cert.StandardFields;
import com.att.cadi.cm.CertException;
import com.att.inno.env.Trans;

public abstract class CA {
	private final String name;
	private String[] trustChain;
	private final StandardFields stdFields;
	private MessageDigest messageDigest;
	private final String permType;
	
	protected CA(String name, StandardFields sf, String permType) {
		this.name = name;
		stdFields = sf;
		this.permType = permType;
	}

	/* 
	 * NOTE: These two functions must be called in Protected Constructors during their Construction.
	 */
	protected void setTrustChain(String[] trustChain) {
		this.trustChain = trustChain;
	}

	protected void setMessageDigest(MessageDigest md) {
		messageDigest = md;
	}

	/*
	 * End Required Constructor calls
	 */

	public String getName() {
		return name;
	}

	public String[] getTrustChain() {
		return trustChain;
	}
	
	public String getPermType() {
		return permType;
	}
	
	public StandardFields stdFields() {
		return stdFields;
	}
	
	public abstract X509Certificate sign(Trans trans, CSRMeta csrmeta) throws IOException, CertException;

	public MessageDigest messageDigest() {
		return messageDigest;
	}
}
