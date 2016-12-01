/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.data.test.obj;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.att.rosetta.marshal.DataWriter;
import com.att.rosetta.marshal.FieldArray;
import com.att.rosetta.marshal.FieldDate;
import com.att.rosetta.marshal.FieldDateTime;
import com.att.rosetta.marshal.FieldHexBinary;
import com.att.rosetta.marshal.FieldNumeric;
import com.att.rosetta.marshal.FieldString;
import com.att.rosetta.marshal.ObjMarshal;

import types.xsd.Multi.Single;

public class SingleMarshal extends ObjMarshal<Single> {
	public SingleMarshal() {
		add(new FieldString<Single>("str") {
			@Override
			protected String data(Single t) {
				return t.getStr();
			}
		});
		
		add(new FieldNumeric<Integer, Single>("int") {
			@Override
			protected Integer data(Single t) {
				return t.getInt();
			}
		});
		
		add(new FieldNumeric<Long,Single>("long") {
			@Override
			protected Long data(Single t) {
				return t.getLong();
			}
		});

		add(new FieldDate<Single>("date") {
			@Override
			protected XMLGregorianCalendar data(Single t) {
				return t.getDate();
			}
		});

		add(new FieldDateTime<Single>("datetime") {
			@Override
			protected XMLGregorianCalendar data(Single t) {
				return t.getDate();
			}
		});
		
		add(new FieldHexBinary<Single>("binary") {
			@Override
			protected byte[] data(Single t) {
				return t.getBinary();
			}
		});
		
		add(new FieldArray<Single,String>("array", DataWriter.STRING) {
			@Override
			protected List<String> data(Single t) {
				return t.getArray();
			}
		});

	}
}