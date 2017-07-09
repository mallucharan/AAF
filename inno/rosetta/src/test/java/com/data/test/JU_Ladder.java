/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.data.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static org.junit.Assert.*;

import com.att.rosetta.Ladder;

public class JU_Ladder {

	@Test
	public void test() {
		Ladder<String> ladder = new Ladder<String>();
		
		for(int i=0;i<30;++i) {
			for(int j=0;j<i;++j)ladder.ascend();
			String str = "Rung " + i;
			assertEquals(ladder.peek(),null);
			ladder.push(str);
			assertEquals(str,ladder.peek());
			assertEquals(str,ladder.pop());
			assertEquals(null,ladder.peek());
			for(int j=0;j<i;++j)ladder.descend();
		}
		assertEquals(ladder.height(),32); // Sizing, when naturally created is by 8
		
		ladder.cutTo(8);
		assertEquals(ladder.height(),8); 
		
		for(int i=0;i<30;++i) {
			ladder.jumpTo(i);
			String str = "Rung " + i;
			assertEquals(ladder.peek(),null);
			ladder.push(str);
			assertEquals(ladder.peek(),str);
		}

		ladder.bottom();
		
		for(int i=0;i<30;++i) {
			assertEquals("Rung " + i,ladder.peek());
			ladder.ascend();
		}
		
		ladder.bottom();
		ladder.top();
		assertEquals("Rung 29",ladder.peek());
		
		for(int i=0;i<30;++i) {
			ladder.jumpTo(i);
			assertEquals("Rung " + i,ladder.peek());
		}

	}

}
