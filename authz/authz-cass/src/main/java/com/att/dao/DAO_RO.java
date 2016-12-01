/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao;

import java.util.List;

import com.att.authz.layer.Result;
import com.att.inno.env.Trans;

/**
 * DataAccessObject - ReadOnly
 * 
 * It is useful to have a ReadOnly part of the interface for CachedDAO
 * 
 * Normal DAOs will implement full DAO
 * 
 *
 * @param <DATA>
 */
public interface DAO_RO<TRANS extends Trans,DATA> {
	/**
	 * Get a List of Data given Key of Object Array
	 * @param objs
	 * @return
	 * @throws DAOException
	 */
	public Result<List<DATA>> read(TRANS trans, Object ... key);

	/**
	 * Get a List of Data given Key of DATA Object
	 * @param trans
	 * @param key
	 * @return
	 * @throws DAOException
	 */
	public Result<List<DATA>> read(TRANS trans, DATA key);

	/**
	 * close DAO
	 */
	public void close(TRANS trans);

	/**
	 * Return name of referenced Data
	 * @return
	 */
	public String table();


}
