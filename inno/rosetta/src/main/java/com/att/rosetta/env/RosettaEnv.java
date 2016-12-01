/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta.env;

import java.applet.Applet;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.validation.Schema;

import com.att.inno.env.APIException;

/**
 * An essential Implementation of Env, which will fully function, without any sort
 * of configuration.
 * 
 * Use as a basis for Group level Env, just overriding where needed.
 *
 */
public class RosettaEnv extends com.att.inno.env.impl.BasicEnv {

	public RosettaEnv() {
		super();
	}

	public RosettaEnv(Applet applet, String... tags) {
		super(applet, tags);
	}

	public RosettaEnv(String[] args) {
		super(args);
	}

	public RosettaEnv(String tag, String[] args) {
		super(tag, args);
	}

	public RosettaEnv(String tag, Properties props) {
		super(tag, props);
	}

	public RosettaEnv(Properties props) {
		super(props);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> RosettaDF<T> newDataFactory(Class<?>... classes) throws APIException {
		return new RosettaDF<T>(this, null, null, (Class<T>)classes[0]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> RosettaDF<T> newDataFactory(Schema schema, Class<?>... classes) throws APIException {
			return new RosettaDF<T>(this, schema, null, (Class<T>)classes[0]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public<T> RosettaDF<T> newDataFactory(QName qName, Class<?> ... classes) throws APIException {
		return new RosettaDF<T>(this, null, qName.getNamespaceURI(),(Class<T>)classes[0]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public<T> RosettaDF<T> newDataFactory(Schema schema, QName qName, Class<?> ... classes) throws APIException {
		return new RosettaDF<T>(this, schema,qName.getNamespaceURI(),(Class<T>)classes[0]);
	}
}
