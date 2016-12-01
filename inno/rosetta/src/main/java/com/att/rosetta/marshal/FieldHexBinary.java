/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta.marshal;

public abstract class FieldHexBinary<T> extends FieldMarshal<T>{
	public FieldHexBinary(String name) {
		super(name);
	}

	protected abstract byte[] data(T t); 

	@Override
	protected boolean data(T t, StringBuilder sb) {
		return DataWriter.HEX_BINARY.write(data(t), sb);
	}
}
