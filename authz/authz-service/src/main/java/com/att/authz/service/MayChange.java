/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.service;

import com.att.authz.layer.Result;

/**
 * There are several ways to determine if 
 *
 */
public interface MayChange {
	public Result<?> mayChange();
}
