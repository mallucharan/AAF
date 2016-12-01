/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen;

public class Back {
	public String str;
	public boolean dec;
	public boolean cr;
	
	public Back(String string, boolean decrement, boolean newline) {
		str = string;
		dec = decrement;
		cr = newline;
	}
}