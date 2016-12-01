/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.data.test;

import java.io.IOException;
import java.io.Writer;

import com.att.inno.env.util.IndentPrintWriter;
import com.att.rosetta.Out;
import com.att.rosetta.Parse;
import com.att.rosetta.ParseException;
import com.att.rosetta.Parsed;

public class OutDump extends Out{

	@Override
	public<IN, S> void extract(IN in, Writer writer, Parse<IN,S> prs, boolean ... options) throws IOException, ParseException {
		IndentPrintWriter ipw = writer instanceof IndentPrintWriter?(IndentPrintWriter)writer:new IndentPrintWriter(writer);

		Parsed<S> p = prs.newParsed();
		
		while((p = prs.parse(in,p.reuse())).valid()) {
			switch(p.event) {
				case Parse.START_OBJ:
					ipw.append("Start Object ");
					ipw.append(p.name);
					ipw.inc();
					break;
				case Parse.END_OBJ: 
					printData(ipw,p);
					ipw.dec();
					ipw.append("End Object ");
					ipw.append(p.name);
					break;
				case Parse.START_ARRAY:
					ipw.inc();
					ipw.append("Start Array ");
					ipw.append(p.name);
					ipw.append('\n');
					break;
				case Parse.END_ARRAY: 
					printData(ipw,p);
					ipw.dec();
					ipw.append("End Array ");
					ipw.append('\n');
					break;
				case Parse.NEXT:
					printData(ipw,p);
					break;
			}
		}
	}
	
	private void printData(IndentPrintWriter ipw, Parsed<?> parsed) {
		if(parsed.hasData()) {
			ipw.append("Data:[");
			if(parsed.hasName()) {
				ipw.append(parsed.name);
				ipw.append(" : "); 
			}
			ipw.append(parsed.sb);
			ipw.append("]");
			ipw.append('\n');
		}
	}

	@Override
	public String logName() {
		return "Rosetta OutDump";
	}

}
