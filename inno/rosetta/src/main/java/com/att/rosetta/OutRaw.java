/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta;

import java.io.IOException;
import java.io.Writer;

public class OutRaw extends Out{

	@Override
	public<IN,S> void extract(IN in, Writer writer, Parse<IN,S> prs, boolean ... options) throws IOException, ParseException {
		Parsed<S> p = prs.newParsed();
		
		while((p = prs.parse(in,p.reuse())).valid()) { 
			writer.append(p.toString());
			writer.append('\n');
		}
	}
	
	@Override
	public String logName() {
		return "Rosetta RAW";
	}



}
