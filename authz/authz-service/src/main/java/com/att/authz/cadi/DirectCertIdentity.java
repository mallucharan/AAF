/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cadi;

import java.nio.ByteBuffer;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.att.authz.env.AuthzTrans;
import com.att.authz.layer.Result;
import com.att.cadi.principal.X509Principal;
import com.att.cadi.taf.cert.CertIdentity;
import com.att.cadi.taf.cert.X509Taf;
import com.att.cssa.rserv.TransFilter;
import com.att.dao.aaf.cached.CachedCertDAO;
import com.att.dao.aaf.cass.CertDAO.Data;

/**
 * Direct view of CertIdentities
 * 
 * Warning:  this class is difficult to instantiate.  The only service that can use it is AAF itself, and is thus 
 * entered in the "init" after the CachedCertDAO is created.
 * 
 *
 */
public class DirectCertIdentity implements CertIdentity {
	private static CachedCertDAO certDAO;

	@Override
	public Principal identity(HttpServletRequest req, X509Certificate cert,	byte[] _certBytes) throws CertificateException {
	    	byte[] certBytes = _certBytes;
		if(cert==null && certBytes==null) {
		    return null;
		}
		if(certBytes==null) {
		    certBytes = cert.getEncoded();
		}
		byte[] fingerprint = X509Taf.getFingerPrint(certBytes);

		AuthzTrans trans = (AuthzTrans) req.getAttribute(TransFilter.TRANS_TAG);
		
		Result<List<Data>> cresp = certDAO.read(trans, ByteBuffer.wrap(fingerprint));
		if(cresp.isOKhasData()) {
			Data cdata = cresp.value.get(0);
			return new X509Principal(cdata.id,cert,certBytes);
		}
		return null;
	}

	public static void set(CachedCertDAO ccd) {
		certDAO = ccd;
	}

}
