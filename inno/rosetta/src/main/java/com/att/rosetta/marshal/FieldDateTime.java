/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta.marshal;

import javax.xml.datatype.XMLGregorianCalendar;

public abstract class FieldDateTime<T> extends FieldMarshal<T> {
	public FieldDateTime(String name) {
		super(name);
	}

	@Override
	final protected boolean data(T t, StringBuilder sb) {
		return DataWriter.DATE_TIME.write(data(t), sb);
	}

	protected abstract XMLGregorianCalendar data(T t); 
}
