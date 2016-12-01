/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen;

import java.io.IOException;

import com.att.inno.env.APIException;
import com.att.inno.env.Env;
import com.att.inno.env.Trans;
import com.att.inno.env.util.StringBuilderWriter;
import com.att.xgen.html.State;

public class XGenBuff<G extends XGen<G>> {
	private G xgen;
	private StringBuilder sb;
	// private String forward, backward;
	
	public XGenBuff(int flags, CacheGen<G> cg) {
		sb = new StringBuilder();
		xgen = cg.create(flags, new StringBuilderWriter(sb));
	}

	/**
	 * Normal case of building up Cached HTML without transaction info
	 * 
	 * @param cache
	 * @param code
	 * @throws APIException
	 * @throws IOException
	 */
	public void run(Cache<G> cache, Code<G> code) throws APIException, IOException {
		code.code(cache, xgen);
	}

	/**
	 * Special Case where code is dynamic, so give access to State and Trans info
	 *  
	 * @param state
	 * @param trans
	 * @param cache
	 * @param code
	 * @throws APIException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void run(State<Env> state, Trans trans, Cache cache, DynamicCode code) throws APIException, IOException {
			code.code(state, trans, cache, xgen);
	}
	
	public int getIndent() {
		return xgen.getIndent();
	}

	public void setIndent(int indent) {
		xgen.setIndent(indent);
	}

	public Section<G> newSection() {
		Section<G> s = new Section<G>();
		s.indent = xgen.getIndent();
		s.forward = sb.toString();
		sb.setLength(0);
		s.backward = sb.toString();
		sb.setLength(0);
		return s;
	}
}
