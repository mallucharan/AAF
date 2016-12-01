/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.old;

import java.io.OutputStream;
import java.io.Writer;

import com.att.inno.env.APIException;
import com.att.inno.env.Env;

/**
 * Allow Extended IO interface usage without muddying up the Stringifier Interface
 */
public interface IOStringifier<T> extends Stringifier<T> {
	/**
	 * Marshal from an Object T onto a Writer, using contents from Env as necessary.<p>
	 * 
	 * Implementations should use the {@link Env} to call "env.startTime(<string>, Env.XML)" to mark
	 * XML time, since this is often a costly process.
	 *
	 * @param env
	 * @param input
	 * @return String
	 * @throws APIException
	 */
	public abstract void stringify(Env env, T input, Writer writer, boolean ... options) throws APIException;
	
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
	public abstract void stringify(Env env, T input, OutputStream os, boolean ... options) throws APIException;

	/**
	 * Set Pretty XML, where possible
	 * 
	 * @param pretty
	 * @throws APIException
	 */
	public abstract IOStringifier<T> pretty(boolean pretty);

	/**
	 * Set Generate Fragment
	 * 
	 * @param fragment
	 * @throws APIException
	 */
	public abstract IOStringifier<T> asFragment(boolean fragment);


}
