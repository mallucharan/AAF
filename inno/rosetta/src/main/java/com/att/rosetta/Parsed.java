/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta;


public class Parsed<S> {
	public static final String EXTENSION_TAG="extension";
	
	public boolean isString;
	
	public StringBuilder sb;
	public char event;
	public String name;
	public S state;

	public Parsed() {
		this(null);
	}

	// Package on purpose
	Parsed(S theState) {
		sb = new StringBuilder();
		isString = false;
		event = Parse.NONE;
		name = "";
		state = theState;
	}

	public boolean valid() {
		return event!=Parse.NONE;
	}
	
	public Parsed<S> reuse() {
		isString=false;
		sb.setLength(0);
		event = Parse.NONE;
		name = "";
		// don't touch T...
		return this;
	}

	public void dataIsName() {
		name = sb.toString();
		sb.setLength(0);
	}

	public boolean hasName() {
		return name.length()>0;
	}

	public boolean hasData() {
		return sb.length()>0;
	}
	
	public String toString() {
		StringBuilder sb2 = new StringBuilder();
		if(event<40)sb2.append((int)event);
		else sb2.append(event);
		sb2.append(" - ");
		sb2.append(name);
		if(sb.length()>0) {
			sb2.append(" : ");
			if(isString)sb2.append('"');
			sb2.append(sb);
			if(isString)sb2.append('"');
		}
		return sb2.toString();
	}

}
