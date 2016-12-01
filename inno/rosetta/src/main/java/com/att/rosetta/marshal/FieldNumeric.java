/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta.marshal;

public abstract class FieldNumeric<N,T> extends FieldMarshal<T> {
	public FieldNumeric(String name) {
		super(name);
	}

	@Override
	final protected boolean data(T t, StringBuilder sb) {
		sb.append(data(t));
		return false;
	}

	protected abstract N data(T t); 
}
