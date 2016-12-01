/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.old;

import java.io.InputStream;
import java.io.Reader;

import com.att.inno.env.APIException;
import com.att.inno.env.Env;

public interface IOObjectifier<T> extends Objectifier<T> {
	/**
	 * Marshal to Object T from a Reader, using contents from Env as necessary.<p>
	 * 
	 * Implementations should use the {@link Env} to call "env.startXMLTime()" to mark
	 * XML time, since this is often a costly process.
	 *
	 * @param env
	 * @param input
	 * @return T
	 * @throws APIException
	 */
	public abstract T objectify(Env env, Reader rdr) throws APIException;
	
	/**
	 * Marshal to Object T from an InputStream, using contents from Env as necessary.<p>
	 * 
	 * Implementations should use the {@link Env} to call "env.startXMLTime()" to mark
	 * XML time, since this is often a costly process.
	 *
	 * @param env
	 * @param input
	 * @return T
	 * @throws APIException
	 */
	public abstract T objectify(Env env, InputStream is) throws APIException;

}
