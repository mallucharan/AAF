/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen.xml;

import java.io.IOException;
import java.io.Writer;

import com.att.inno.env.APIException;
import com.att.xgen.CacheGen;
import com.att.xgen.Code;

public class XMLCacheGen extends CacheGen<XMLGen> {

	public XMLCacheGen(int flags, Code<XMLGen> code) throws APIException,
			IOException {
		super(flags, code);
	}

	@Override
	public XMLGen create(int style, Writer w) {
		XMLGen xg = new XMLGen(w);
		xg.pretty = (style & PRETTY)==PRETTY;
		return xg;
	}

}
