/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.cm;

import java.io.File;

import certman.v1_0.Artifacts.Artifact;
import certman.v1_0.CertInfo;

import com.att.cadi.CadiException;
import com.att.cadi.util.Chmod;
import com.att.inno.env.Trans;

public class PlaceArtifactInFiles extends ArtifactDir {
	@Override
	public boolean _place(Trans trans, CertInfo certInfo, Artifact arti) throws CadiException {
		try {
			// Setup Public Cert
			File f = new File(dir,arti.getAppName()+".crt");
			write(f,Chmod.to644,certInfo.getCerts().get(0),C_R);
			
			// Setup Private Key
			f = new File(dir,arti.getAppName()+".key");
			write(f,Chmod.to400,certInfo.getPrivatekey(),C_R);
			
		} catch (Exception e) {
			throw new CadiException(e);
		}
		return true;
	}
}


