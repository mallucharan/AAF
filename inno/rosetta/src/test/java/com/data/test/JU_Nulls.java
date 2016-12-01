/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.data.test;

import org.junit.AfterClass;
import org.junit.Test;

import com.att.inno.env.Data;
import com.att.rosetta.env.RosettaDF;
import com.att.rosetta.env.RosettaData;
import com.att.rosetta.env.RosettaEnv;

import junit.framework.Assert;
import s.xsd.LargerData;
import s.xsd.SampleData;

public class JU_Nulls {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		RosettaEnv env = new RosettaEnv();
		try {
			RosettaDF<LargerData> df = env.newDataFactory(LargerData.class);
			df.out(Data.TYPE.JSON);
			LargerData urr = new LargerData();
			SampleData sd = new SampleData();
			sd.setDate(1444125487798L);
			sd.setId(null);
			urr.getSampleData().add(sd);
			urr.setFluff(null);
			RosettaData<LargerData> data = df.newData();
//			StringWriter sw = new StringWriter();
//			df.direct(trans, urr, sw);
//			System.out.println(sw.toString());
			data.load(urr);
			System.out.println(data.asString());
			Assert.assertEquals("{\"SampleData\":[{\"date\":1444125487798}]}", data.asString());
			
			System.out.println(data.out(Data.TYPE.RAW).asString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
