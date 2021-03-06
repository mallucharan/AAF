/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.test;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.att.cadi.Capacitor;

public class JU_Capacitor {
	@Test
	public void testA() {
		Capacitor cap = new Capacitor();
		
		for(int iter=0;iter<200;++iter) {
			for(int i=0;i<20;++i) {
				cap.put((byte)('a'+i));
			}
			cap.setForRead();
			byte[] array = new byte[20];
			for(int i=0;i<20;++i) {
				array[i]=(byte)cap.read();
			}
			assertEquals("abcdefghijklmnopqrst",new String(array));
			assertEquals(-1,cap.read());
			cap.done();
		}
	}

	public final static String TEST_DATA = 
			"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
			"bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
			"cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc" +
			"dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
			"eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
			"ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";
	@Test
	public void testB() {
		Capacitor cap = new Capacitor();
		byte[] arrayA = TEST_DATA.getBytes();
		System.out.println(arrayA.length);
		for(int iter=0;iter<200;++iter) {
			for(int i=0;i<arrayA.length;++i) {
				cap.put(arrayA[i]);
			}
			cap.setForRead();
			assertEquals(TEST_DATA.length(),cap.available());
			byte[] arrayB = new byte[arrayA.length];
			for(int i=0;i<arrayB.length;++i) {
				arrayB[i]=(byte)cap.read();
			}
			assertEquals(TEST_DATA,new String(arrayB));
			assertEquals(-1,cap.read());
			cap.done();
		}
	}

	@Test
	public void testC() {
		Capacitor cap = new Capacitor();
		byte[] arrayA = TEST_DATA.getBytes();
		System.out.println(arrayA.length);
		for(int iter=0;iter<200;++iter) {
			cap.put(arrayA,0,arrayA.length);
			cap.setForRead();
			assertEquals(TEST_DATA.length(),cap.available());
			byte[] arrayB = new byte[arrayA.length];
			assertEquals(arrayA.length,cap.read(arrayB,0,arrayB.length));
			assertEquals(TEST_DATA,new String(arrayB));
			assertEquals(-1,cap.read());
			cap.done();
		}
	}

	
	@Test
	public void testD() {
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<300;++i) {
			sb.append(TEST_DATA);
			sb.append("###FILLER##");
		}
		String td = sb.toString();
		Capacitor cap = new Capacitor();
		byte[] arrayA = td.getBytes();
		System.out.println(arrayA.length);
		for(int iter=0;iter<200;++iter) {
			cap.put(arrayA,0,arrayA.length);
			cap.setForRead();
			assertEquals(td.length(),cap.available());
			byte[] arrayB = new byte[arrayA.length];
			assertEquals(arrayA.length,cap.read(arrayB,0,arrayB.length));
			assertEquals(td,new String(arrayB));
			assertEquals(-1,cap.read());
			cap.done();
		}
	}

	@Test
	public void testE() {
		Capacitor cap = new Capacitor();
		
		String b = "This is some content that we want to read";
		byte[] a = b.getBytes();
		byte[] c = new byte[b.length()]; // we want to use this to test reading offsets, etc

		for(int i=0;i<a.length;i+=11) {
			cap.put(a, i, Math.min(11,a.length-i));
		}
		cap.reset();
		int read;
		for(int i=0;i<c.length;i+=read) {
			read = cap.read(c, i, Math.min(3,c.length-i));
			if(read<0) break;
		}
		
		assertEquals(b, new String(c));
		
		
	}
	

}
