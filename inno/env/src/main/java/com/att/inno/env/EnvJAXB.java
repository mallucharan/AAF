/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;

import javax.xml.namespace.QName;
import javax.xml.validation.Schema;

public interface EnvJAXB extends EnvStore<TransJAXB> {
	/**
	 * Obtain a DataInterface from this Environment
	 * 
	 * @param <T>
	 * @param classes
	 * @return
	 * @throws APIException
	 */
	public <T> DataFactory<T> newDataFactory(Class<?>... classes) throws APIException;

	/**
	 * Obtain a DataInterface from this Environment, with Validating Schema
	 * 
	 * @param <T>
	 * @param classes
	 * @return
	 * @throws APIException
	 */
	public <T> DataFactory<T> newDataFactory(Schema schema, Class<?>... classes) throws APIException;

	public<T> DataFactory<T> newDataFactory(QName qName, Class<?> ... classes)	throws APIException;

	public<T> DataFactory<T> newDataFactory(Schema schema, QName qName, Class<?> ... classes) throws APIException;
	
}
