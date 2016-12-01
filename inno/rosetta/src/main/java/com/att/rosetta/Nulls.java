/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.att.inno.env.Env;
import com.att.inno.env.TimeTaken;

public class Nulls {
	public static final Parse<Reader, ?> IN = new Parse<Reader, Void>() {

		// @Override
		public Parsed<Void> parse(Reader r, Parsed<Void> parsed)throws ParseException {
			parsed.event = Parse.END_DOC;
			return parsed;
		}

		// @Override
		public Parsed<Void> newParsed() {
			Parsed<Void> parsed = new Parsed<Void>();
			parsed.event = Parse.END_DOC;
			return parsed;
		}

		// @Override
		public TimeTaken start(Env env) {
			return env.start("IN", Env.SUB);
		}
		
	};
	
	public static final Out OUT = new Out() {

		// @Override
		public <IN,S> void extract(IN in, Writer writer, Parse<IN, S> parse, boolean ... options)throws IOException, ParseException {
		}
		@Override
		public String logName() {
			return "Rosetta NULL";
		}


	};
}
