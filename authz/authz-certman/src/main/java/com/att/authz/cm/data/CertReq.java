/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cm.data;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.att.authz.cm.ca.CA;
import com.att.authz.cm.cert.BCFactory;
import com.att.authz.cm.cert.CSRMeta;
import com.att.cadi.cm.CertException;

public class CertReq {
	// These cannot be null
	public CA certAuthority;
	public String mechid;
	public List<String> fqdns;
	// Notify
	public List<String> emails;
	
	
	// These may be null
	public String sponsor;
	public XMLGregorianCalendar start, end;
	
	public CSRMeta getCSRMeta() throws CertException {
		return BCFactory.createCSRMeta(certAuthority, mechid, sponsor,fqdns);
	}
}
