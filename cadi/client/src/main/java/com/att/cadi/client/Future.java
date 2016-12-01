/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.client;

import com.att.cadi.CadiException;

public abstract class Future<T> {
	public T value;
	public abstract boolean get(int timeout) throws CadiException;
	
	public abstract int code();
	public abstract String body();
	public abstract String header(String tag);
}