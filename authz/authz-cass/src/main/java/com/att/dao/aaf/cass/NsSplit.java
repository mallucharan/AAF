/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.cass;

public class NsSplit {
	public final String ns;
	public final String name;
	public final NsDAO.Data nsd;
	
	public NsSplit(NsDAO.Data nsd, String child) {
		this.nsd = nsd;
		if(child.startsWith(nsd.name)) {
			ns = nsd.name;
			int dot = ns.length();
			if(dot<child.length() && child.charAt(dot)=='.') {
    			name = child.substring(dot+1);
			} else {
				name="";
			}
		} else {
			name=null;
			ns = null;
		}
	}
	
	public NsSplit(String ns, String name) {
		this.ns = ns;
		this.name = name;
		this.nsd = new NsDAO.Data();
		nsd.name = ns;
		int dot = ns.lastIndexOf('.');
		if(dot>=0) {
			nsd.parent = ns.substring(0, dot);
		} else {
			nsd.parent = ".";
		}
	}

	public boolean isOK() {
		return ns!=null && name !=null;
	}
}