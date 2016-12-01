/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.att.authz.layer.Result;
import com.att.dao.aaf.cass.DelegateDAO;
import com.att.dao.aaf.cass.DelegateDAO.Data;


public class JU_DelegateDAO  extends AbsJUCass {
	@Test
	public void testCRUD() throws Exception {
		DelegateDAO dao = new DelegateDAO(trans, cluster, AUTHZ);
		DelegateDAO.Data data = new DelegateDAO.Data();
		data.user = "jg1555";
		data.delegate = "rd8227";
		data.expires = new Date();
		
//        Bytification
        ByteBuffer bb = data.bytify();
        Data bdata = new DelegateDAO.Data();
        bdata.reconstitute(bb);
        compare(data, bdata);

		try {
			// Test create
			Result<Data> ddcr = dao.create(trans,data);
			assertTrue(ddcr.isOK());
			
			
			// Read by User
			Result<List<DelegateDAO.Data>> records = dao.read(trans,data.user);
			assertTrue(records.isOKhasData());
			for(DelegateDAO.Data rdata : records.value) 
				compare(data,rdata);

			// Read by Delegate
			records = dao.readByDelegate(trans,data.delegate);
			assertTrue(records.isOKhasData());
			for(DelegateDAO.Data rdata : records.value) 
				compare(data,rdata);
			
			// Update
			data.delegate = "pf2819";
			data.expires = new Date();
			assertTrue(dao.update(trans, data).isOK());

			// Read by User
			records = dao.read(trans,data.user);
			assertTrue(records.isOKhasData());
			for(DelegateDAO.Data rdata : records.value) 
				compare(data,rdata);

			// Read by Delegate
			records = dao.readByDelegate(trans,data.delegate);
			assertTrue(records.isOKhasData());
			for(DelegateDAO.Data rdata : records.value) 
				compare(data,rdata);

			// Test delete
			dao.delete(trans,data, true);
			records = dao.read(trans,data.user);
			assertTrue(records.isEmpty());
			
			
		} finally {
			dao.close(trans);
		}
	}
	
	private void compare(Data d1, Data d2) {
		assertEquals(d1.user, d2.user);
		assertEquals(d1.delegate, d2.delegate);
		assertEquals(d1.expires,d2.expires);
	}


}
