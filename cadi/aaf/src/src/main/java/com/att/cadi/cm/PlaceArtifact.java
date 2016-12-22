/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.cm;

import certman.v1_0.Artifacts.Artifact;
import certman.v1_0.CertInfo;

import com.att.cadi.CadiException;
import com.att.inno.env.Trans;

public interface PlaceArtifact {
	public boolean place(Trans trans, CertInfo cert, Artifact arti) throws CadiException;
}
