/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;




/**
 * A Trans is like an Env, however, it's purpose it to track the Transient 
 * Data associated with Transactions, or other short term elements.
 * 
 * Any Object implementing Trans should expect to go in an out of scope quickly
 * 
 * Implementations should also overload the concepts of "Start", etc and build up
 * and Audit Log, so it can implement "metric" below
 * 
 * All Transactions (i.e. a call to a service) will need these items.
 * 
 *
 */
public interface Trans extends Env {
	/**
	 * Add a completed entry in the Audit Trail for tracking purposes.
	 * 
	 * @param text
	 */
	public void checkpoint(String text);

	/**
	 * Add a completed entry in the Audit Trail for tracking purposes, and combine flag with "CHECKPOINT" 
	 * 
	 * @param text
	 */
	public void checkpoint(String text, int additionalFlag);

	/**
	 * Output an Audit Trail onto the StringBuilder
	 *
	 * Load metrics into an array of floats from passed in Flags
	 * 
	 * @param flag
	 * @param sb
	 * @return	 
	 */
	public Metric auditTrail(int indent, StringBuilder sb, int ... flag);

	public class Metric {
		public float[] buckets;
		public float   total;
		public int     entries;
	}
}