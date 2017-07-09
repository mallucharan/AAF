/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen.html;

import java.io.IOException;
import java.io.Writer;

import com.att.inno.env.APIException;
import com.att.xgen.CacheGen;
import com.att.xgen.Code;

public class HTMLCacheGen extends CacheGen<HTMLGen> {
	protected int flags;

	public HTMLCacheGen(int flags, Code<HTMLGen> code) throws APIException,IOException {
		super(flags, code);
		this.flags = flags;
	}

	@Override
	public HTMLGen create(int htmlStyle, Writer w) {
		HTMLGen hg;
		switch(htmlStyle&(CacheGen.HTML4|CacheGen.HTML5)) {
			case CacheGen.HTML4:
				hg = new HTML4Gen(w);
				break;
			case CacheGen.HTML5:
			default:
				hg = new HTML5Gen(w);
				break;

		}
		hg.pretty = (htmlStyle&CacheGen.PRETTY)>0;
		return hg;
	}

	protected HTMLGen clone(Writer w) {
		return create(flags,w);
	}
}
