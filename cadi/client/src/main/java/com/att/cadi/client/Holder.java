/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.client;

/**
 * Use to set Variables outside of Anonymous classes.
 *
 *
 * @param <T>
 */
public class Holder<T> {
	private T value;
	public Holder(T t) {
		value = t;
	}
	public void set(T t) {
		value = t;
	}
	
	public T get() {
		return value;
	}

}
