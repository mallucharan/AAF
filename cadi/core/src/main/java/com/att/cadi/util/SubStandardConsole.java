/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Substandard, because System.in doesn't do Passwords..
public class SubStandardConsole implements MyConsole {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	@Override
	public String readLine(String fmt, Object... args) {
		String rv;
		try {
			System.out.printf(fmt,args);
			rv = br.readLine();
			if(args.length==1 && rv.length()==0) {
				rv = args[0].toString();
			}
		} catch (IOException e) {
			System.err.println("uh oh...");
			rv = "";
		}
		return rv;
	}

	@Override
	public char[] readPassword(String fmt, Object... args) {
		try {
			System.out.printf(fmt,args);
			return br.readLine().toCharArray();
		} catch (IOException e) {
			System.err.println("uh oh...");
			return new char[0];
		}
	}

	@Override
	public void printf(String fmt, Object... args) {
		System.out.printf(fmt, args);
	}
}