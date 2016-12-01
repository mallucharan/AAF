/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.dao.aaf.test;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.att.authz.env.AuthzTrans;
import com.att.authz.layer.Result;
import com.att.dao.CIDAO;
import com.att.dao.DAOException;
import com.att.dao.aaf.cass.CacheInfoDAO;
import com.att.dao.aaf.cass.RoleDAO;
import com.att.dao.aaf.cass.Status;
import com.att.inno.env.APIException;
import com.att.inno.env.util.Chrono;

import junit.framework.Assert;


public class JU_CacheInfoDAO extends AbsJUCass {

	@Test
	public void test() throws DAOException, APIException, IOException {
		CIDAO<AuthzTrans> id = new CacheInfoDAO(trans, cluster, AUTHZ);
		Date date  = new Date();
		
		id.touch(trans, RoleDAO.TABLE,1);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		Result<Void> rid = id.check(trans);
		Assert.assertEquals(rid.status,Status.OK);
		Date[] dates = CacheInfoDAO.info.get(RoleDAO.TABLE);
		if(dates.length>0 && dates[1]!=null) {
			System.out.println(Chrono.dateStamp(dates[1]));
			System.out.println(Chrono.dateStamp(date));
			Assert.assertTrue(Math.abs(dates[1].getTime() - date.getTime())<20000); // allow for 4 seconds, given Remote DB
		}
	}

}
