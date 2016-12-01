/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao;
/**
 * Interface to obtain Segment Integer from DAO Data
 * for use in Caching mechanism
 * 
 * This should typically be obtained by getting the Hash of the key, then using modulus on the size of segment.
 * 
 *
 */
public interface Cacheable {
	public int[] invalidate(Cached<?,?> cache);
}
