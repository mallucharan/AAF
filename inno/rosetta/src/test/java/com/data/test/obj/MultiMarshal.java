/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.data.test.obj;

import java.util.List;

import com.att.rosetta.marshal.ObjArray;
import com.att.rosetta.marshal.ObjMarshal;

import types.xsd.Multi;
import types.xsd.Multi.Single;

public class MultiMarshal extends ObjMarshal<Multi> {
	public MultiMarshal() {
		add(new ObjArray<Multi,Single>("single",new SingleMarshal()) {
			@Override
			protected List<Single> data(Multi t) {
				return t.getSingle();
			}
		});
	}
}