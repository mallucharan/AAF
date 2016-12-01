/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta;

class Prop {
	public String tag;
	public String value;
	public Prop(String t, String v) {
		tag = t;
		value =v;
	}
	
	public Prop(String t_equals_v) {
		String[] tv = t_equals_v.split("=");
		if(tv.length>1) {
			tag = tv[0];
			value = tv[1];
		}				
	}

	public String toString() {
		return tag + '=' + value;
	}
}