/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao;

import java.util.Date;

import com.att.authz.layer.Result;
import com.att.inno.env.Trans;

public interface CIDAO<TRANS extends Trans> {

	/**
	 * Touch the date field for given Table
	 *  
	 * @param trans
	 * @param name
	 * @return
	 */
	public abstract Result<Void> touch(TRANS trans, String name, int ... seg);

	/**
	 * Read all Info entries, and set local Date objects
	 * 
	 * This is to support regular data checks on the Database to speed up Caching behavior
	 * 
	 */
	public abstract Result<Void> check(TRANS trans);

	public abstract Date get(TRANS trans, String table, int seg);

}