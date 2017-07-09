/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.principal;

import java.security.Principal;

import com.att.cadi.UserChain;

public class TrustPrincipal extends BearerPrincipal implements UserChain {
	private final String name;
	private final Principal original;
	private String userChain;
	
	public TrustPrincipal(final Principal actual, final String asName) {
		this.original = actual;
		name = asName.trim();
		if(actual instanceof UserChain) {
			UserChain uc = (UserChain)actual;
			userChain = uc.userChain();
		} else if(actual instanceof X509Principal) {
			userChain="x509";
		} else if(actual instanceof BasicPrincipal) {
			userChain="BAth";
		} else {
			userChain = actual.getClass().getSimpleName();
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public String getOrigName() {
		return original.getName() + '[' + userChain + ']';
	}

	@Override
	public String userChain() {
		return userChain;
	}
	
	public Principal original() {
		return original;
	}
	
}
