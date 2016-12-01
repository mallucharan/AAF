/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen;

import java.io.IOException;

import com.att.inno.env.APIException;
import com.att.inno.env.Env;
import com.att.inno.env.Trans;
import com.att.xgen.html.State;

/**
 * Special Code Interface to gain access to Transaction
 * and State information
 *
 */
public abstract class DynamicCode<G extends XGen<G>, AS extends State<Env>, TRANS extends Trans> implements Code<G> {
	public abstract void code(AS state, TRANS trans, Cache<G> cache, G xgen) throws APIException, IOException;
	
	// We expect not to have this section of the code engaged at any time
	public void code(Cache<G> cache, G xgen) throws APIException, IOException {
		code(null, null,cache,xgen);
	}
}
