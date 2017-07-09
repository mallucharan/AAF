/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.config;

import com.att.cadi.PropAccess;

public class GetAccess extends PropAccess {
	private final Get getter;
	
	public GetAccess(Get getter) {
		super(new String[]{"cadi_prop_files="+getter.get("cadi_prop_files", null, true)});
		this.getter = getter;
	}
	
	/* (non-Javadoc)
	 * @see com.att.cadi.PropAccess#getProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public String getProperty(String tag, String def) {
		String rv;
		rv = super.getProperty(tag, null);
		if(rv==null && getter!=null) {
			rv = getter.get(tag, null, true);
		}
		return rv==null?def:rv;
	}
	/* (non-Javadoc)
	 * @see com.att.cadi.PropAccess#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(String tag) {
		String rv;
		rv = super.getProperty(tag, null);
		if(rv==null && getter!=null) {
			rv = getter.get(tag, null, true);
		}
		return rv;
	}

	public Get get() {
		return getter;
	}
}
