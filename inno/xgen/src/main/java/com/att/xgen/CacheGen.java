/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.att.inno.env.APIException;
import com.att.inno.env.Env;
import com.att.inno.env.Trans;
import com.att.xgen.html.State;
import com.att.xgen.html.Thematic;


public abstract class CacheGen<G extends XGen<G>> {
	public static final int NO_FLAGS = 0x0;
	public final static int PRETTY	= 0x1;
	public final static int XML		= 0x2;
	public final static int HTML4 	= 0x4;
	public final static int HTML5 	= 0x8;

	
	private ArrayList<Section<G>> sections = new ArrayList<Section<G>>();
	private int flags;
	private final Thematic thematic;

	public CacheGen(int flags, Code<G> code) throws APIException, IOException {
		this.flags = flags;
		final XGenBuff<G> buff = new XGenBuff<G>(flags,this);
		// Run to gather Strings and Code Class Segments
		buff.run(new Cache<G>() {
				@Override
				public void dynamic(G hgen, Code<G> code) {
					sections.add(buff.newSection());
					sections.add(new Dynamic(hgen.getIndent(),code));
				}
			},code);
		sections.add(buff.newSection());
	
		// If Code implements thematic, set for later
		thematic = code instanceof Thematic?(Thematic)code:null;

	}
	
	public abstract G create(int htmlStyle, Writer w);

	public void replay(State<Env> state, Trans trans, OutputStream os, String theme) throws IOException, APIException {
		replay(state, trans, new OutputStreamWriter(os), theme);
	}
	
	public void replay(State<Env> state, Trans trans,Writer w, String theme) throws IOException, APIException {
		if(thematic!=null) {
			theme = thematic.themeResolve(theme);
		}
		/* Theme
		trans.setTheme(theme);
		int htmlStyle = state.htmlVer(theme);
		*/
		
		XGenBuff<G> buff = new XGenBuff<G>(flags,this);
		
		// forward
		int indent = 0;
		Section<G> s;
		int i=0;
		@SuppressWarnings("unchecked")
		Section<G>[] reverse = new Section[sections.size()];
		for(Section<G> section : sections) {
			s = section.use(state, trans, buff); // note, doesn't change cached, only dynamic, which is created for thread
			int tempIndent = s.getIndent();
			s.setIndent(indent);
			s.forward(w);
			s.setIndent(tempIndent);
			indent = tempIndent;
			reverse[i++]=s;
		}

		for(--i;i>=0;--i) {
			reverse[i].back(w);
		}
		w.flush();
	}
	
	private class Dynamic extends Section<G> {
		private Code<G> code;
		
		public Dynamic(int indent, Code<G> code) {
			this.code = code;
			this.indent = indent;
		}

		@SuppressWarnings("unchecked")
		public Section<G> use(State<Env> state, Trans trans, XGenBuff<G> buff) throws APIException, IOException {
			// Clone Dynamic to make Thread Safe
			Dynamic d = new Dynamic(indent,code);
			buff.setIndent(indent);
			if(code instanceof DynamicCode) {
				buff.run(state,trans,Cache.Null.singleton(), (DynamicCode<G,?,? extends Trans>)code);
			} else {
				buff.run((Cache<G>)Cache.Null.singleton(), code);
			}
			Section<G> s = buff.newSection();
			d.indent = s.indent;
			d.forward = s.forward;
			d.backward = s.backward;
			return d;
		}
	}
}
