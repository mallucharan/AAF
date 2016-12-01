/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.local.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import com.att.authz.local.DataFile;
import com.att.authz.local.DataFile.Token;
import com.att.authz.local.DataFile.Token.Field;

public class JU_DataFile {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws Exception {
		File file = new File("../authz-batch/data/v1.dat");
		DataFile df = new DataFile(file,"r");
		int count = 0;
		List<String> list = new ArrayList<String>();
		try {
			df.open();
			Token tok = df.new Token(1024000);
			Field fld = tok.new Field('|');
	
			while(tok.nextLine()) {
				++count;
				fld.reset();
				list.add(fld.at(0));
			}
//			Collections.sort(list);
			for(String s: list) {
				System.out.println(s);

			}
		} finally {
			System.out.printf("%15s:%12d\n","Total",count);
		}
	}

}
