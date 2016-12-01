/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.service;

import com.att.authz.env.AuthzTrans;
import com.att.authz.facade.AuthzFacade;
import com.att.cssa.rserv.HttpCode;

public abstract class Code extends HttpCode<AuthzTrans, AuthzFacade> implements Cloneable {
	public boolean useJSON;

	public Code(AuthzFacade facade, String description, boolean useJSON, String ... roles) {
		super(facade, description, roles);
		this.useJSON = useJSON;
	}
	
	public <D extends Code> D clone(AuthzFacade facade, boolean useJSON) throws Exception {
		@SuppressWarnings("unchecked")
		D d = (D)clone();
		d.useJSON = useJSON;
		d.context = facade;
		return d;
	}
	
}