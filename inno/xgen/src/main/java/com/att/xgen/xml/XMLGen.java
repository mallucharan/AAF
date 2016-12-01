/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen.xml;

import java.io.Writer;

import com.att.xgen.XGen;;

public class XMLGen extends XGen<XMLGen> {
	private final String XML_TAG; 
	
	public XMLGen(Writer w) {
		this(w,"UTF-8");
	}
	
	public XMLGen(Writer w, String encoding) {
		super(w);
		XML_TAG="<?xml version=\"1.0\" encoding=\"" + encoding + "\" standalone=\"yes\"?>"; 
	}

	public XMLGen xml() {
		forward.println(XML_TAG);
		return this;
	}
}
