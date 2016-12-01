/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.impl;

import javax.xml.namespace.QName;
import javax.xml.validation.Schema;

import com.att.inno.env.APIException;
import com.att.inno.env.DataFactory;
import com.att.inno.env.EnvJAXB;
import com.att.inno.env.TransJAXB;

public abstract class AbsTransJAXB extends AbsTrans<EnvJAXB> implements TransJAXB {
	public AbsTransJAXB(EnvJAXB env) {
		super(env);
	}
	
//	@Override
	public <T> DataFactory<T> newDataFactory(Class<?>... classes) throws APIException {
		return delegate.newDataFactory(classes);
	}

//	@Override
	public <T> DataFactory<T> newDataFactory(Schema schema, Class<?>... classes) throws APIException {
		return delegate.newDataFactory(schema, classes);
	}

//	@Override
	public <T> DataFactory<T> newDataFactory(QName qName, Class<?>... classes) throws APIException {
		return delegate.newDataFactory(qName, classes);
	}

//	@Override
	public <T> DataFactory<T> newDataFactory(Schema schema, QName qName, Class<?>... classes) throws APIException {
		return delegate.newDataFactory(schema, qName, classes);
	}

}
