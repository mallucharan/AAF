/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen;

import java.io.IOException;
import java.io.Writer;

import com.att.inno.env.APIException;
import com.att.inno.env.Env;
import com.att.inno.env.Trans;
import com.att.xgen.html.State;

public class Section<G extends XGen<G>> {
	protected int indent;
	protected String forward;
	protected String backward;
	
	// Default is to use the set Strings (static) 
	public Section<G> use(State<Env> state, Trans trans, XGenBuff<G> buff) throws APIException, IOException {
		return this;
	}
	
	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}

	public void forward(Writer w) throws IOException {
		w.write(forward);
	}
	
	public void back(Writer w) throws IOException {
		w.write(backward);
	}
	
	public String toString() {
		return forward;
	}
}