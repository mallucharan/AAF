/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.util;

public interface MyConsole {
	public String readLine(String fmt, Object ... args);
	public char[] readPassword(String fmt, Object ... args);
	public void printf(String fmt, Object ...args);
}