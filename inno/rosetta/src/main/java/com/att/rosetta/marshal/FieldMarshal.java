/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta.marshal;


import com.att.rosetta.Marshal;
import com.att.rosetta.Parse;
import com.att.rosetta.Parsed;

public abstract class FieldMarshal<T> extends Marshal<T> {
	private String name;

	public FieldMarshal(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Parsed<State> parse(T t, Parsed<State> parsed) {
		parsed.state.ladder.push(DONE_ITERATOR);
		parsed.event = Parse.NEXT;
		parsed.name = name;
		parsed.isString = data(t,parsed.sb);
		return parsed;
	}

	/**
	 * Write Value to StringBuilder
	 * Return true if value looks like a String
	 *        false if it is Numeric
	 * @param t
	 * @param sb
	 * @return
	 */
	protected abstract boolean data(T t, StringBuilder sb);
	
}