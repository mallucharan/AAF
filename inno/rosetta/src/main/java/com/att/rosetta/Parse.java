/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta;

import com.att.inno.env.Env;
import com.att.inno.env.TimeTaken;

public interface Parse<IN, S> {
	public Parsed<S> parse(IN in, Parsed<S> parsed) throws ParseException;
	
	// EVENTS
	public static final char NONE = 0;
	public static final char START_DOC = 1;
	public static final char END_DOC = 2;
	public static final char ATTRIB = 3;
	
	public static final char NEXT = ',';
	public static final char START_OBJ = '{';
	public static final char END_OBJ = '}';
	public static final char START_ARRAY = '[';
	public static final char END_ARRAY = ']';
	
	public Parsed<S> newParsed() throws ParseException;
	public TimeTaken start(Env env); 
	
}
