/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen;

public class Mark {
	// package on purpose
	int spot = 0;
	public String comment;
	
	public Mark() {
		comment = null; 
	}
	
	public Mark(String string) {
		comment = string;
	}

	public void spot(int spot) {
		this.spot = spot;
	}
}