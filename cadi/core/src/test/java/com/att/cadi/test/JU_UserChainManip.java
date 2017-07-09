/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.att.cadi.util.UserChainManip;

public class JU_UserChainManip {

	@Test
	public void test() {
		assertEquals("",UserChainManip.idToNS(null));
		assertEquals("",UserChainManip.idToNS(""));
		assertEquals("",UserChainManip.idToNS("something"));
		assertEquals("",UserChainManip.idToNS("something@@"));
		assertEquals("",UserChainManip.idToNS("something@@."));
		assertEquals("com",UserChainManip.idToNS("something@com"));
		assertEquals("com.random",UserChainManip.idToNS("something@random.com"));
		assertEquals("com.random",UserChainManip.idToNS("@random.com"));
		assertEquals("com.random",UserChainManip.idToNS("something@random.com."));
		assertEquals("com.random",UserChainManip.idToNS("something@..random...com..."));
		assertEquals("com.random.this",UserChainManip.idToNS("something@this.random.com"));
	}

}
