/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.att.inno.env.LogTarget;

/**
 * This LogTarget Implementation is included mostly because the JavaUtil based logging is included in the
 * JDK.  This makes the default implementation independent of any external Jars.
 * 
 *  Log4j is often considered more Enterprise capable.  See Log4JLogTarget for that implementation
 * 
 *
 */
public class JavaUtilLogTarget implements LogTarget {
	private Level level;
	private Logger log;

	public JavaUtilLogTarget(Logger logger, Level theLevel) {
		log = logger;
		level = theLevel;
	}

	public boolean isLoggable() {
		return log.isLoggable(level);
	}

	public void log(Object ... msgs) {
		if(log.isLoggable(level)) {
			StringBuilder sb = new StringBuilder();
			String msg;
			for(int i=0;i<msgs.length;++i) {
				msg = msgs[i].toString();
				if(msg!=null && msg.length()>0) {
					int sbl = sb.length();
					if(sbl>0) {
						char last = sb.charAt(sbl-1);
						if(" (.".indexOf(last)<0 && "().".indexOf(msg.charAt(0))<0)sb.append(' ');
					}
					sb.append(msg);
				}
			}
			log.log(level, sb.toString());
		}
	}

	public void log(Throwable e, Object ... msgs) {
		String str = e.getLocalizedMessage();
		if(str==null) {
			str = e.getMessage();
		}
		if(str==null) {
			str = e.getClass().getName();
		}
		log.log(level,str,msgs);
	}

	/* (non-Javadoc)
	 * @see com.att.inno.env.LogTarget#printf(java.lang.String, java.lang.String[])
	 */
	@Override
	public void printf(String fmt, Object ... vars) {
		if(log.isLoggable(level)) {
			log.log(level,String.format(fmt,vars));
		}
	}
}	
