/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.org;

public class OrganizationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrganizationException() {
		super();
	}

	public OrganizationException(String message) {
		super(message);
	}

	public OrganizationException(Throwable cause) {
		super(cause);
	}

	public OrganizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrganizationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
