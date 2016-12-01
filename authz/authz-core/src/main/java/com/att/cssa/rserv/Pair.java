/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cssa.rserv;

/**
 * A pair of generic Objects.  
 *
 * @param <X>
 * @param <Y>
 */
public class Pair<X,Y> {
	public X x;
	public Y y;
	
	public Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "X: " + x.toString() + "-->" + y.toString();
	}
}
