/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.jaxb;

import java.io.InputStream;
import java.io.Reader;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;

import com.att.inno.env.APIException;
import com.att.inno.env.Env;
import com.att.inno.env.TimeTaken;
import com.att.inno.env.old.IOObjectifier;

/**
 * Allow Extended IO interface usage without muddying up the Stringifier Interface
 */
public class JAXBObjectifier<T> implements IOObjectifier<T> {
	private JAXBumar jumar;

	public JAXBObjectifier(Schema schema, Class<?>... classes) throws APIException {
		try {
			jumar = new JAXBumar(schema, classes);
		} catch (JAXBException e) {
			throw new APIException(e);
		}
	}

	public JAXBObjectifier(Class<?>... classes) throws APIException {
		try {
			jumar = new JAXBumar(classes);
		} catch (JAXBException e) {
			throw new APIException(e);
		}
	}
	
    // package on purpose
	JAXBObjectifier(JAXBumar jumar) {
		this.jumar = jumar;
	}

	@SuppressWarnings("unchecked")
	// @Override
	public T objectify(Env env, String input) throws APIException {
		TimeTaken tt = env.start("JAXB Unmarshal", Env.XML);
		try {
			tt.size(input.length());
			return (T)jumar.unmarshal(env.debug(), input);
		} catch (JAXBException e) {
			throw new APIException(e);
		} finally {
			tt.done();
		}
	}

	@SuppressWarnings("unchecked")
	// @Override
	public T objectify(Env env, Reader rdr) throws APIException {
		//TODO create a Reader that Counts?
		TimeTaken tt = env.start("JAXB Unmarshal", Env.XML);
		try {
			return (T)jumar.unmarshal(env.debug(), rdr);
		} catch (JAXBException e) {
			throw new APIException(e);
		} finally {
			tt.done();
		}
	}


	@SuppressWarnings("unchecked")
	// @Override
	public T objectify(Env env, InputStream is) throws APIException {
		//TODO create a Reader that Counts?
		TimeTaken tt = env.start("JAXB Unmarshal", Env.XML);
		try {
			return (T)jumar.unmarshal(env.debug(), is);
		} catch (JAXBException e) {
			throw new APIException(e);
		} finally {
			tt.done();
		}
	}


	public void servicePrestart(Env env) throws APIException {
	}

	public void threadPrestart(Env env) throws APIException {
	}

	// // @Override
	public void refresh(Env env) throws APIException {
	}

	// // @Override
	public void threadDestroy(Env env) throws APIException {
	}

	// // @Override
	public void serviceDestroy(Env env) throws APIException {
	}


	@SuppressWarnings("unchecked")
	public T newInstance() throws APIException {
		try {
			return (T)jumar.newInstance();
		} catch (Exception e) {
			throw new APIException(e);
		}
	}

}

