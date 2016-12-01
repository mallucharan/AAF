/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta;

public interface JaxEval{
	public abstract JaxEval eval(Parsed<?> p) throws ParseException;
}
