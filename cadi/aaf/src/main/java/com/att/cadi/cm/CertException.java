/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.cm;

public class CertException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1373028409048516401L;

	public CertException() {
	}

	public CertException(String message) {
		super(message);
	}

	public CertException(Throwable cause) {
		super(cause);
	}

	public CertException(String message, Throwable cause) {
		super(message, cause);
	}
}