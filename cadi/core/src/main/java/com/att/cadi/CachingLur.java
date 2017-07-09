/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi;

import java.security.Principal;

import com.att.cadi.CachedPrincipal.Resp;


public interface CachingLur<PERM extends Permission> extends Lur {
	public abstract void remove(String user);
	public abstract Resp reload(User<PERM> user);
	public abstract void setDebug(String commaDelimIDsOrNull);
	public abstract void clear(Principal p, StringBuilder sb);
}
