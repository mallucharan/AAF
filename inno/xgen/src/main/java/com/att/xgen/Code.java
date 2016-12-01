/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen;

import java.io.IOException;

import com.att.inno.env.APIException;

public interface Code<G extends XGen<G>> {
	public void code(Cache<G> cache, G xgen) throws APIException, IOException;
}