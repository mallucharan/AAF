/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.old;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import com.att.inno.env.APIException;
import com.att.inno.env.Data;
import com.att.inno.env.DataFactory;
import com.att.inno.env.Env;

public interface OldDataFactory<T> extends DataFactory<T> {
	public abstract String stringify(T type) throws APIException;
	public abstract void stringify(T type, OutputStream os)	throws APIException;
	public abstract void stringify(T type, Writer writer) throws APIException;
	public abstract T objectify(InputStream is) throws APIException;
	public abstract T objectify(Reader rdr) throws APIException;
	public abstract T objectify(String text) throws APIException;
	public abstract T newInstance() throws APIException;
	public abstract Data<T> newData(T type);
	public abstract Data<T> newDataFromStream(Env env, InputStream input) throws APIException;
	public abstract Data<T> newDataFromString(String string);
	
}

