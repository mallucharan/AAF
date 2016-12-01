/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.layer;

import javax.servlet.http.HttpServletResponse;

import com.att.inno.env.Data;
import com.att.inno.env.Data.TYPE;



public abstract class FacadeImpl {
	protected static final String IN = "in";

	protected void setContentType(HttpServletResponse response, TYPE type) {
		response.setContentType(type==Data.TYPE.JSON?"application/json":"text.xml");
	}
}
