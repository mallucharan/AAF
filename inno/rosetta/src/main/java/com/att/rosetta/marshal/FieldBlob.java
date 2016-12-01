/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta.marshal;

public abstract class FieldBlob<T> extends FieldMarshal<T>{
	public FieldBlob(String name) {
		super(name);
	}

	protected abstract byte[] data(T t); 

	@Override
	protected boolean data(T t, StringBuilder sb) {
		return false;
		// unimplemented 
		//return DataWriter.BYTE_ARRAY.write(data(t),sb);
	}

}
