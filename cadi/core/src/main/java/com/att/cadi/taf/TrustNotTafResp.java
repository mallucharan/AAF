/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.taf;

import java.io.IOException;
import java.security.Principal;

import com.att.cadi.Access;

public class TrustNotTafResp implements TafResp {
	private final TafResp delegate;
	private final String desc;
	
	public TrustNotTafResp(final TafResp delegate, final String desc) {
		this.delegate = delegate;
		this.desc = desc;
	}
	
	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public String desc() {
		return desc;
	}

	@Override
	public RESP isAuthenticated() {
		return RESP.NO_FURTHER_PROCESSING;
	}

	@Override
	public RESP authenticate() throws IOException {
		return RESP.NO_FURTHER_PROCESSING;
	}

	@Override
	public Principal getPrincipal() {
		return delegate.getPrincipal();
	}

	@Override
	public Access getAccess() {
		return delegate.getAccess();
	}

	@Override
	public boolean isFailedAttempt() {
		return true;
	}
	
	public String toString() {
		return desc();
	}
}
