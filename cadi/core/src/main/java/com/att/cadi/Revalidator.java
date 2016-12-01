/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi;


public interface Revalidator<TRANS> {
	/**
	 * Re-Validate Credential
	 * 
	 * @param prin
	 * @return
	 */
	public CachedPrincipal.Resp revalidate(TRANS trans, CachedPrincipal prin);

}
