/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.util;

public class TheConsole implements MyConsole {
	@Override
	public String readLine(String fmt, Object... args) {
		String rv = System.console().readLine(fmt, args);
		if(args.length>0 && args[0]!=null && rv.length()==0) {
			rv = args[0].toString();
		}
		return rv;
	}

	@Override
	public char[] readPassword(String fmt, Object... args) {
		return System.console().readPassword(fmt, args);
	}
	
	public static boolean implemented() {
		return System.console()!=null;
	}

	@Override
	public void printf(String fmt, Object... args) {
		System.console().printf(fmt, args);
	}
}