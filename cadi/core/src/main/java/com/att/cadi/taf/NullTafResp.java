/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.taf;

import java.io.IOException;
import java.security.Principal;

import com.att.cadi.Access;

/**
 * A Null Pattern for setting responses to "Deny" before configuration is setup.
 *
 */
class NullTafResp implements TafResp {
	private NullTafResp(){}
	
	private static TafResp singleton = new NullTafResp();
	
	public static TafResp singleton() {
		return singleton;
	}
	
	public boolean isValid() {
		return false;
	}
	
	public RESP isAuthenticated() {
		return RESP.NO_FURTHER_PROCESSING;
	}
	
	public String desc() {
		return "All Authentication denied";
	}
	
	public RESP authenticate() throws IOException {
		return RESP.NO_FURTHER_PROCESSING;
	}

	public Principal getPrincipal() {
		return null;
	}

	public Access getAccess() {
		return Access.NULL;
	}

	/* (non-Javadoc)
	 * @see com.att.cadi.taf.TafResp#isFailedAttempt()
	 */
	public boolean isFailedAttempt() {
		return true;
	}
}
