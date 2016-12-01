/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.env;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.att.authz.org.Organization;
import com.att.cadi.Lur;
import com.att.cadi.Permission;
import com.att.inno.env.LogTarget;
import com.att.inno.env.TransStore;

public interface AuthzTrans extends TransStore {
	public abstract AuthzTrans set(HttpServletRequest req);

	public abstract void setUser(Principal p);
	
	public abstract String user();

	public abstract Principal getUserPrincipal();

	public abstract String ip();

	public abstract int port();

	public abstract String meth();

	public abstract String path();

	public abstract String agent();
	
	public abstract AuthzEnv env();

	public abstract void setLur(Lur lur);

	public abstract boolean fish(Permission p);
	
	public abstract boolean forceRequested();
	
	public abstract Organization org();

	public abstract boolean moveRequested();

	public abstract boolean futureRequested();
	
	public abstract void logAuditTrail(LogTarget lt);

}