/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.cass;

import com.att.dao.Cacheable;
import com.att.dao.Cached;
import com.att.dao.CachedDAO;

public abstract class CacheableData implements Cacheable {
	// WARNING:  DON'T attempt to add any members here, as it will 
	// be treated by system as fields expected in Tables
	protected int seg(Cached<?,?> cache, Object ... fields) {
		return cache==null?0:cache.invalidate(CachedDAO.keyFromObjs(fields));
	}
	
}
