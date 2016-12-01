/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.data.test;

import java.io.StringReader;

import org.junit.Test;

import com.att.inno.env.Data;
import com.att.inno.env.Data.TYPE;
import com.att.inno.env.TimeTaken;
import com.att.inno.env.Trans;
import com.att.inno.env.impl.EnvFactory;
import com.att.inno.env.jaxb.JAXBmar;
import com.att.inno.env.util.StringBuilderWriter;
import com.att.rosetta.env.RosettaDF;
import com.att.rosetta.env.RosettaData;
import com.att.rosetta.env.RosettaEnv;

import s.xsd.LargerData;
import s.xsd.Multi;

public class JU_RosettaDF {
	public static int ITERATIONS = 1;

	@Test
	public void testCached() throws Exception {
		RosettaEnv env = new RosettaEnv();
		RosettaDF<LargerData> df = env.newDataFactory(LargerData.class);
		JAXBmar jmar = new JAXBmar(LargerData.class);

		StringBuilderWriter sbw = new StringBuilderWriter(1024);
		Trans trans = EnvFactory.newTrans();

		Report report = new Report(ITERATIONS,"Load JSON","Extract JAXB", "JAXB Marshal", "Cached to XML", "Cached to JSON");
		do {
			sbw.reset();
			trans = EnvFactory.newTrans();
			Data<LargerData> data;
			TimeTaken tt = trans.start("Load JSON", 1);
			try {
				data = df.newData(trans).out(Data.TYPE.JSON).in(Data.TYPE.JSON).load(JU_FromJSON.str);
			} finally {
				tt.done();
			}
			LargerData ld;
			tt = trans.start("Extract JAXB", 2);
			try {
				ld = data.asObject();
			} finally {
				tt.done();
			}

			tt = trans.start("JAXB marshal", 3);
			try {
				jmar.marshal(trans.debug(), ld, sbw);
			} finally {
				tt.done();
			}
			sbw.append('\n');
			
			tt = trans.start("To XML from Cache",4);
			try {
				data.out(Data.TYPE.XML).to(sbw);
			} finally {
				tt.done();
			}
			
			sbw.append('\n');
			
			tt = trans.start("To JSON from Cache",5);
			try {
				data.out(Data.TYPE.JSON).to(sbw);
			} finally {
				tt.done();
			}
			report.glean(trans, 1,2,3,4,5);
		} while(report.go());
		
		report.report(sbw);
		System.out.println(sbw);
		
	}

	@Test
	public void testDirect() throws Exception {
		RosettaEnv env = new RosettaEnv();
		RosettaDF<LargerData> df = env.newDataFactory(LargerData.class);

		StringBuilderWriter sbw = new StringBuilderWriter(1024);
		Trans trans = EnvFactory.newTrans();

		Report report = new Report(ITERATIONS);
		do {
			sbw.reset();
			trans = EnvFactory.newTrans();
			RosettaData<?> data = df.newData(trans).in(Data.TYPE.JSON).out(Data.TYPE.XML);
			data.direct(new StringReader(JU_FromJSON.str), sbw);
			report.glean(trans);
		} while(report.go());
		
		report.report(sbw);
		System.out.println(sbw);
		
	}
	
	@Test
	public void testMulti() throws Exception {
		RosettaEnv env = new RosettaEnv();
		RosettaDF<Multi> df = env.newDataFactory(Multi.class);

//		StringBuilderWriter sbw = new StringBuilderWriter(1024);
//		Trans trans = EnvFactory.newTrans();

		Multi m = new Multi();
		m.getF1().add("String1");
		m.getF2().add("String2");
		
		System.out.println(df.newData().load(m).out(TYPE.RAW).asString());
		System.out.println(df.newData().load(m).out(TYPE.JSON).asString());
		
	}

}
