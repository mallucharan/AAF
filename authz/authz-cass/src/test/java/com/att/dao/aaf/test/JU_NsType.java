/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.test;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Test;

import com.att.dao.aaf.cass.NsType;

public class JU_NsType {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		NsType nt,nt2;
		String[] tests = new String[] {"DOT","ROOT","COMPANY","APP","STACKED_APP","STACK"};
		for(String s : tests) {
			nt = NsType.valueOf(s);
			assertEquals(s,nt.name());
			
			nt2 = NsType.fromString(s);
			assertEquals(nt,nt2);
			
			int t = nt.type;
			nt2 = NsType.fromType(t);
			assertEquals(nt,nt2);
		}
		
		nt  = NsType.fromType(Integer.MIN_VALUE);
		assertEquals(nt,NsType.UNKNOWN);
		nt = NsType.fromString("Garbage");
		assertEquals(nt,NsType.UNKNOWN);
	}

}
