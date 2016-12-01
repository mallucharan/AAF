/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.data.test;

import org.junit.Test;

import com.att.rosetta.JaxInfo;

import s.xsd.LargerData;

public class JU_Struct {
	public final static String XML ="<LargerData xmlns=\"urn:s:xsd\">\n" +
									    "<SampleData>\n" +
									        "<id>sd object 1</id>\n" +
									        "<date>1346439215932</date>\n" +
									        "<item>Item 1.1</item>\n" +
									        "<item>Item 1.2</item>\n" +
									    "</SampleData>\n" +
									    "<SampleData>\n" +
									        "<id>sd object 2</id>\n" +
									        "<date>1346439215932</date>\n" +
									        "<item>Item 2.1</item>\n" +
									        "<item>Item 2.2</item>\n" +
									    "</SampleData>\n" +
									    "<fluff>MyFluff</fluff>\n" +
									"</LargerData>\n";
	
//	@Test
//	public void test2() throws Exception  {
//
//		SampleData sd = new SampleData();
//		sd.setDate(new Date().getTime());
//		sd.setId("myId");
//		sd.getItem().add("Item 1.1");
//		
//		InObj<SampleData> inObj = new InObj<SampleData>(SampleData.class);
//
//		JaxSet<SampleData> jaxSet = JaxSet.get(SampleData.class);
//	 	Setter<SampleData> setter = jaxSet.setter("id");
//	 	setter.set(sd, "Your ID");
//	 	
//	 	for(Entry<String, Getter<SampleData>> es : jaxSet.getters()) {
//	 		System.out.print(es.getKey());
//	 		System.out.print(' ');
//	 		System.out.println(es.getValue().get(sd));
//	 	}
//	}
	
	@Test
	public void test() throws Exception  {
		JaxInfo ji = JaxInfo.build(LargerData.class);
		System.out.println(ji);
	}

}
