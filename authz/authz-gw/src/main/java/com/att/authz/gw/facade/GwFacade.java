/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.gw.facade;

import javax.servlet.http.HttpServletResponse;

import com.att.authz.env.AuthzTrans;
import com.att.authz.layer.Result;
import com.att.cssa.rserv.RServlet;


/**
 *   
 *
 */
public interface GwFacade {

/////////////////////  STANDARD ELEMENTS //////////////////
	/** 
	 * @param trans
	 * @param response
	 * @param result
	 */
	void error(AuthzTrans trans, HttpServletResponse response, Result<?> result);

	/**
	 * 
	 * @param trans
	 * @param response
	 * @param status
	 */
	void error(AuthzTrans trans, HttpServletResponse response, int status,	String msg, String ... detail);


	/**
	 * 
	 * @param trans
	 * @param resp
	 * @param rservlet
	 * @return
	 */
	public Result<Void> getAPI(AuthzTrans trans, HttpServletResponse resp, RServlet<AuthzTrans> rservlet);

	/**
	 * 
	 * @param trans
	 * @param resp
	 * @param typeCode
	 * @param optional
	 * @return
	 */
	public abstract Result<Void> getAPIExample(AuthzTrans trans, HttpServletResponse resp, String typeCode, boolean optional);

}