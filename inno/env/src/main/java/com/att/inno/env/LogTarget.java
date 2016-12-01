/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;

import java.io.PrintStream;
import java.util.Date;

import com.att.inno.env.util.Chrono;

/**
 * LogTarget is the interface with which to assign any kind of Logging Implementations.
 * 
 * Implement for any Logging Library of your choice, and for any logging string Format desired.
 * 
 * Included are several Static Implementations for various uses:
 * 	 NULL: Does nothing with Logging Messages
 *   SYSOUT: Writes messages in general form to System Out
 *   SYSERR: Writes messages in general form to System Err
 *   
 *
 */
public interface LogTarget {
	public abstract void log(Object... msgs);
	public abstract void log(Throwable e, Object ... msgs);
	public abstract boolean isLoggable();
	public abstract void printf(String fmt, Object ... vars);

	// A Convenient LogTarget to insert when a NO-OP is desired.
	public static final LogTarget NULL = new LogTarget() {
		public void log(Object ... msgs) {
		}

		public void log(Throwable t, Object ... msgs) {
		}

		public boolean isLoggable() {
			return false;
		}

		@Override
		public void printf(String fmt, Object ... vars) {
		}
	};

	// A Convenient LogTarget to write to the Console
	public static final LogTarget SYSOUT = new LogTarget() {
		public void log(Object ... msgs) {
			PrintStream out = System.out;
			out.print(com.att.inno.env.util.Chrono.dateFmt.format(new Date()));
			out.print(": ");
			for(Object str : msgs) {
				if(str!=null) {
					out.print(str.toString());
					out.print(' ');
				} else {
					out.print("null ");
				}
			}
			out.println();
		}

		public void log(Throwable t, Object ... msgs) {
			PrintStream out = System.out;
			out.print(Chrono.dateFmt.format(new Date()));
			out.print(": ");
			for(Object str : msgs) {
				out.print(str.toString());
				out.print(' ');
			}
			out.println();
			t.printStackTrace(out);
			out.println();
		}

		public boolean isLoggable() {
			return true;
		}

		@Override
		public void printf(String fmt, Object ... vars) {
			log(String.format(fmt,vars));
		}
	};
	
	// A Convenient LogTarget to write to the Console
	public static final LogTarget SYSERR = new LogTarget() {
		public void log(Object ... msgs) {
			PrintStream out = System.err;
			out.print(Chrono.dateFmt.format(new Date()));
			out.print(": ");
			for(Object str : msgs) {
				out.print(str.toString());
				out.print(' ');
			}
			out.println();
			out.flush();
		}

		public void log(Throwable t, Object ... msgs) {
			PrintStream out = System.err;
			out.print(Chrono.dateFmt.format(new Date()));
			out.print(": ");
			for(Object str : msgs) {
				out.print(str.toString());
				out.print(' ');
			}
			out.println();
			t.printStackTrace(out);
		}

		public boolean isLoggable() {
			return true;
		}
		@Override
		public void printf(String fmt, Object ... vars) {
			log(String.format(fmt,vars));
		}

	};


};