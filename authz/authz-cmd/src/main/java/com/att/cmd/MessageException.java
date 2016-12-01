/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
/**
 * 
 */
package com.att.cmd;

/**
 * An Exception designed simply to give End User message, no stack trace
 * 
 *
 */
public class MessageException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8143933588878259048L;

	/**
	 * @param Message
	 */
	public MessageException(String msg) {
		super(msg);
	}

}
