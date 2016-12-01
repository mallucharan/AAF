/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.old;

import com.att.inno.env.APIException;
import com.att.inno.env.Env;
import com.att.inno.env.LifeCycle;


/**
 * <h1>Stringifier</h1>
 * <i>Stringifier</i> abstracts the marshaling of a String to an Object
 */
public interface Stringifier<T> extends LifeCycle {
	
	/**
	 * Marshal from a String to an Object T, using contents from Env as necessary.<p>
	 * 
	 * Implementations should use the {@link Env} to call "env.startXMLTime()" to mark
	 * XML time, since this is often a costly process.
	 *
	 * @param env
	 * @param input
	 * @return String
	 * @throws APIException
	 */
	public abstract String stringify(Env env, T input, boolean ... options) throws APIException;
	
}