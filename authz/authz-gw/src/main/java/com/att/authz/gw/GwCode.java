/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.gw;

import com.att.authz.env.AuthzTrans;
import com.att.authz.gw.facade.GwFacade;
import com.att.cssa.rserv.HttpCode;

public abstract class GwCode extends HttpCode<AuthzTrans, GwFacade> implements Cloneable {
	public boolean useJSON;

	public GwCode(GwFacade facade, String description, boolean useJSON, String ... roles) {
		super(facade, description, roles);
		this.useJSON = useJSON;
	}
	
	public <D extends GwCode> D clone(GwFacade facade, boolean useJSON) throws Exception {
		@SuppressWarnings("unchecked")
		D d = (D)clone();
		d.useJSON = useJSON;
		d.context = facade;
		return d;
	}
	
}