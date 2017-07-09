/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/


package com.att.cadi;
import java.util.Enumeration;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import com.att.cadi.config.Config;

public class ServletContextAccess extends PropAccess {

	private ServletContext context;

	public ServletContextAccess(FilterConfig filterConfig) {
		super(filterConfig); // protected contstructor... does not have "init" called.
		context = filterConfig.getServletContext();

		for(Enumeration<?> en = filterConfig.getInitParameterNames();en.hasMoreElements();) {
			String name = (String)en.nextElement();
			setProperty(name, filterConfig.getInitParameter(name));
		}
		init(getProperties());
		Config.getDME2Props(this);
	}

	/* (non-Javadoc)
	 * @see com.att.cadi.PropAccess#log(com.att.cadi.Access.Level, java.lang.Object[])
	 */
	@Override
	public void log(Level level, Object... elements) {
		if(willLog(level)) {
			StringBuilder sb = buildMsg(level, elements);
			context.log(sb.toString());
		}
	}

	/* (non-Javadoc)
	 * @see com.att.cadi.PropAccess#log(java.lang.Exception, java.lang.Object[])
	 */
	@Override
	public void log(Exception e, Object... elements) {
		StringBuilder sb = buildMsg(Level.ERROR, elements);
		context.log(sb.toString(),e);
	}

	public ServletContext context() {
		return context;
	}
}
