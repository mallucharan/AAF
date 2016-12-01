/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.taf;

import java.io.IOException;
import java.security.Principal;

import com.att.cadi.Access;

public class TrustTafResp implements TafResp {
	private final TafResp delegate;
	private final Principal principal;
	private final String desc;
	
	public TrustTafResp(final TafResp delegate, final Principal principal, final String desc) {
		this.delegate = delegate;
		this.principal = principal;
		this.desc = desc + ' ' + delegate.desc();
	}
	
	@Override
	public boolean isValid() {
		return delegate.isValid();
	}

	@Override
	public String desc() {
		return desc;
	}

	@Override
	public RESP isAuthenticated() {
		return delegate.isAuthenticated();
	}

	@Override
	public RESP authenticate() throws IOException {
		return delegate.authenticate();
	}

	@Override
	public Principal getPrincipal() {
		return principal;
	}

	@Override
	public Access getAccess() {
		return delegate.getAccess();
	}

	@Override
	public boolean isFailedAttempt() {
		return delegate.isFailedAttempt();
	}
	
	public String toString() {
		return principal.getName() + " by trust of " + desc();
	}
}
