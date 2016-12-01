/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;


public interface DataFactory<T> {
	public abstract Data<T> newData();
	public abstract Data<T> newData(Env trans); // and Env or Trans object
	public abstract Class<T> getTypeClass();
}

