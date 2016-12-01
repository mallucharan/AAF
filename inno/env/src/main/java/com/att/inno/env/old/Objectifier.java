/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
/**
 * 
 */
package com.att.inno.env.old;

import com.att.inno.env.APIException;
import com.att.inno.env.Env;
import com.att.inno.env.LifeCycle;


/**
 * <h1>Objectifier</h1>
 * <i>Objectifier</i> abstracts the unmarshaling of an Object from a String, and 
 * the creation of an uninitialized object. 
 */
public interface Objectifier<T> extends LifeCycle {
	/**
	 * Marshal to Object T from a String, using contents from Env as necessary.<p>
	 * 
	 * Implementations should use the {@link Env} to call "env.startXMLTime()" to mark
	 * XML time, since this is often a costly process.
	 *
	 * @param env
	 * @param input
	 * @return T
	 * @throws APIException
	 */
	public abstract T objectify(Env env, String input) throws APIException;

	/**
	 * Create a new object of type T.  This is often more efficiently done with
	 * the underlying XML (or other) Library.
	 * @return T
	 * @throws APIException
	 */
	public abstract T newInstance() throws APIException;

	
}