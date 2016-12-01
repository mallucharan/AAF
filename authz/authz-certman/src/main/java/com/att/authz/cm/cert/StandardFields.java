/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cm.cert;

import com.att.cadi.cm.CertException;

public interface StandardFields {
	public void set(CSRMeta csr) throws CertException;
}
