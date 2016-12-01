/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta;

import java.io.IOException;
import java.io.Writer;

public class OutJax extends Out {
	private JaxEval jaxEval;

	public OutJax(JaxEval je) {
		this.jaxEval = je;
	}

	@Override
	public <IN,S> void extract(IN in, Writer writer, Parse<IN, S> parse, boolean... options) throws IOException, ParseException {
		Parsed<S> p = parse.newParsed();
		JaxEval je = this.jaxEval;
		while((p = parse.parse(in,p.reuse())).valid()) {
			if(je==null)throw new ParseException("Incomplete content");
			je = je.eval(p);
		}
		
	}
	
	@Override
	public String logName() {
		return "Rosetta JAX";
	}



}
