/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd;

public class Param {
	public final String tag;
	public final boolean required;
	
	/**
	 * 
	 * @param t
	 * @param b
	 */
	public Param(String t, boolean required) {
		tag = t;
		this.required=required;
	}
}
