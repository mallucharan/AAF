package com.att.cadi.principal;

import java.security.Principal;

public abstract class BearerPrincipal implements Principal {
	private String bearer = null;
	public BearerPrincipal setBearer(String bearer) {
		this.bearer = bearer;
		return this;
	}
	public String getBearer() {
		return bearer;
	}
}
