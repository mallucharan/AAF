/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.impl;

import com.att.inno.env.EnvJAXB;
import com.att.inno.env.TransCreate;
import com.att.inno.env.TransJAXB;

/**
 * EnvFactory
 * 
 */
public class EnvFactory {

	public static final String SCHEMA_DIR = "env-schema_dir";
	public static final String DEFAULT_SCHEMA_DIR = "src/main/xsd";
	static BasicEnv singleton;

	static {
		singleton = new BasicEnv();
	}
	public static BasicEnv singleton() {
		return singleton;
	}
	
	public static void setSingleton(BasicEnv be) {
		singleton = be;
	}
	
	public static TransJAXB newTrans() {
		return new BasicTrans(singleton);
	}

	public static TransJAXB newTrans(EnvJAXB env) {
		return new BasicTrans(env);
	}
	
	public static TransCreate<TransJAXB> transCreator() {
		return new TransCreate<TransJAXB>() {
			// @Override
			public BasicTrans newTrans() {
				return singleton.newTrans();
			}
		};
	}
}

