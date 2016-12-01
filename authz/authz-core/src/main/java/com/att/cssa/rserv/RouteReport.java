/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cssa.rserv;

import java.util.ArrayList;
import java.util.List;

public class RouteReport {
	public HttpMethods meth;
	public String path;
	public String desc;
	public final List<String> contextTypes = new ArrayList<String>();

}
