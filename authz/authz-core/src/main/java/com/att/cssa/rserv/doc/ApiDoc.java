/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cssa.rserv.doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.att.cssa.rserv.HttpMethods;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiDoc {
	HttpMethods method();
	String path();
	int expectedCode();
	int[] errorCodes();
	String[] text();
	/** Format with name|type|[true|false] */
	String[] params();
	
}
