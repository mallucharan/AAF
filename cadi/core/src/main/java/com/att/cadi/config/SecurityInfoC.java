/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.config;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.att.cadi.Access;
import com.att.cadi.SecuritySetter;


public class SecurityInfoC<CLIENT> extends SecurityInfo {
	public SecuritySetter<CLIENT> defSS;

	public SecurityInfoC(Access access) throws GeneralSecurityException, IOException {
		super(access);
	}

	public SecurityInfoC<CLIENT> set(SecuritySetter<CLIENT> defSS) {
		this.defSS = defSS;
		return this;
	}

}
