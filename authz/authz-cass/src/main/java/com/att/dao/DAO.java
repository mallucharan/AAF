/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao;

import com.att.authz.layer.Result;
import com.att.inno.env.Trans;


/**
 * DataAccessObject Interface
 *
 * Extend the ReadOnly form (for Get), and add manipulation methods
 *
 * @param <DATA>
 */
public interface DAO<TRANS extends Trans,DATA> extends DAO_RO<TRANS,DATA> {
	public Result<DATA> create(TRANS trans, DATA data);
	public Result<Void> update(TRANS trans, DATA data);
	// In many cases, the data has been correctly read first, so we shouldn't read again
	// Use reread=true if you are using DATA with only a Key
	public Result<Void> delete(TRANS trans, DATA data, boolean reread);
	public Object[] keyFrom(DATA data);
}
