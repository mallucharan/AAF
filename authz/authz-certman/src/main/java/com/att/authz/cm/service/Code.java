/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cm.service;

import com.att.authz.cm.facade.Facade1_0;
import com.att.authz.env.AuthzTrans;
import com.att.cssa.rserv.HttpCode;

public abstract class Code extends HttpCode<AuthzTrans,Facade1_0> implements Cloneable {

	public Code(CertManAPI cma, String description, String ... roles) {
		super(cma.facade1_0, description, roles);
		// Note, the first "Code" will be created with default Facade, "JSON".
		// use clone for another Code with XML
	}
	

	public <D extends Code> D clone(Facade1_0 facade) throws Exception {
		@SuppressWarnings("unchecked")
		D d = (D)clone();
		d.context = facade;
		return d;
	}

}
