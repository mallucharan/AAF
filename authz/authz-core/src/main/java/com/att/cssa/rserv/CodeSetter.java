/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cssa.rserv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.att.inno.env.Trans;

// Package on purpose.  only want between RServlet and Routes
class CodeSetter<TRANS extends Trans> {
	private HttpCode<TRANS,?> code;
	private TRANS trans;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	public CodeSetter(TRANS trans, HttpServletRequest req, HttpServletResponse resp) {
		this.trans = trans;
		this.req = req;
		this.resp = resp;
				
	}
	public boolean matches(Route<TRANS> route) throws IOException, ServletException {
		// Find best Code in Route based on "Accepts (Get) or Content-Type" (if exists)
		return (code = route.getCode(trans, req, resp))!=null;
	}
	
	public HttpCode<TRANS,?> code() {
		return code;
	}
}