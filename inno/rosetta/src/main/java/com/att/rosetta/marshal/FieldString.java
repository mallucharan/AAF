/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta.marshal;

public abstract class FieldString<T> extends FieldMarshal<T> {
	public FieldString(String name) {
		super(name);
	}

	protected abstract String data(T t); 

	@Override
	final protected boolean data(T t, StringBuilder sb) {
		return DataWriter.STRING.write(data(t), sb);
	}

}
