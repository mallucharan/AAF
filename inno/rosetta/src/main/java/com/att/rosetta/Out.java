/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public abstract class Out {
	public abstract<IN,S> void extract(IN in, Writer writer, Parse<IN, S> parse, boolean ... options) throws IOException, ParseException;
	
	public<IN,S> void extract(IN in, OutputStream os, Parse<IN, S> parse, boolean ... options) throws IOException, ParseException {
		Writer w = new OutputStreamWriter(os);
		try {
			extract(in, w, parse, options);
		} finally {
			w.flush();
		}
	}
	
	public abstract String logName();
	
}
