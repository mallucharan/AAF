/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.cached;

import java.util.List;

import com.att.authz.env.AuthzTrans;
import com.att.authz.layer.Result;
import com.att.dao.CIDAO;
import com.att.dao.CachedDAO;
import com.att.dao.aaf.cass.CertDAO;

public class CachedCertDAO extends CachedDAO<AuthzTrans, CertDAO, CertDAO.Data> {
	public CachedCertDAO(CertDAO dao, CIDAO<AuthzTrans> info) {
		super(dao, info, CertDAO.CACHE_SEG);
	}
	
	/**
	 * Pass through Cert ID Lookup
	 * 
	 * @param trans
	 * @param ns
	 * @return
	 */
	
	public Result<List<CertDAO.Data>> readID(AuthzTrans trans, final String id) {
		return dao().readID(trans, id);
	}
	
	public Result<List<CertDAO.Data>> readX500(AuthzTrans trans, final String x500) {
		return dao().readX500(trans, x500);
	}


}
