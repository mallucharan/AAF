/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;


/**
 * <h1>Creatable</h1>
 * <b>**Must implement constructor T(ENV env, long currentTimeMillis);**</b><p>
 *
 * This interface exists to cover basic LifeCycle semantics so that Objects
 * can be created dynamically and managed at a basic level (destroy(env)).
 * 
 *
 * @param <T>
 */
public interface Creatable<T> {
	/**
	 * Return the timestamp (Unix long) when this object was created.<p>
	 * This can be used to see if the object is out of date in certain
	 * circumstances, or perhaps has already been notified in others.
	 * 
	 * @return long
	 */
	public abstract long created();
	
	/**
	 * Allow LifeCycle aware process to signal this element as destroyed.
	 *  
	 * @param env
	 */
	public abstract void destroy(Env env);
}
