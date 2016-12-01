/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.cached;

import com.att.authz.env.AuthzTrans;
import com.att.dao.CIDAO;
import com.att.dao.CachedDAO;
import com.att.dao.aaf.cass.NsDAO;

public class CachedNSDAO extends CachedDAO<AuthzTrans, NsDAO, NsDAO.Data> {
	public CachedNSDAO(NsDAO dao, CIDAO<AuthzTrans> info) {
		super(dao, info, NsDAO.CACHE_SEG);
	}
}
