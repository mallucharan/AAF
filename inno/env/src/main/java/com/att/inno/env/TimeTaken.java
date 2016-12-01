/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;

/**
 * <h1>TimeTaken</h1>
 * This simple interface allows for many different kinds of 
 * Audit Logs to be accomplished, by assuming that the creation
 * of this object indicates "start", and the calling of "done" 
 * ends.
 * 
 * The implementor of this class can easily be stored in efficient
 * mechanisms to minimize impact of Auditing on performance.
 * 
 *
 */
public abstract class TimeTaken {
	public final long start;
	protected long end, size;
	public final int flag;
	public final String name;
	
	/**
	 * The name is as it will appear when written to output (abstract method)
	 * 
	 * The flag is an integer which can be System type (XML, REMOTE, etc), or End User defined for reporting purposes 
	 * 
	 * @param name
	 * @param flag
	 */
	public TimeTaken(String name, int flag) {
		start = System.nanoTime();
		this.flag = flag;
		this.name = name;
		size = -1;
	}


 	/**
	 * Call this when process is done to state ending time.<p>
	 * 
	 * It is <i>exceedingly prudent</i> to wrap the process called with a try-finally:<p>
	 * 
	 * <pre>
	 *   TimeTaken tt = env.startSubTime();
	 *   try {
	 *       process.me(); // code to be timed.
	 *   } finally {
	 *   	 tt.done();
	 *   }
	 * </pre>
	 */
	public void done() {
		end = System.nanoTime();
	}
	
	
	/**
	 * For sizable contents, set the size.  Implementations can simply write a no-op if they don't wish to 
	 * store the size. 
	 * 
	 * @param size
	 */
	public void size(long theSize) {
		size = theSize;
	}
	
	/**
	 * Give readonly access to End, which isn't final
	 * @return
	 */
	public long end() {
		return end;
	}
	
	/**
	 * Time is taken in NanoSeconds.  This method converts to decimals of Milliseconds
	 * @return
	 */
	public float millis() {
		return (end-start)/1000000f;
	}
	/**
	 * Write self to a String Builder (for making Audits)
	 * @param sb
	 */
	public abstract void output(StringBuilder sb);
	
	/**
	 * For Debugging
	 */
	public String toString() {
		return name + ' ' + millis() + "ms " + (size>0?Long.toString(size):"");
	}
	
}		
