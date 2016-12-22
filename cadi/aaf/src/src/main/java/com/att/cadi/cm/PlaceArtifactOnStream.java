/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.cm;

import java.io.PrintStream;

import certman.v1_0.Artifacts.Artifact;
import certman.v1_0.CertInfo;

import com.att.inno.env.Trans;

public class PlaceArtifactOnStream implements PlaceArtifact {
	private PrintStream out;

	public PlaceArtifactOnStream(PrintStream printStream) {
		out = printStream;
	}

	@Override
	public boolean place(Trans trans, CertInfo capi, Artifact a) {
		if(capi.getNotes()!=null && capi.getNotes().length()>0) {
			trans.info().printf("Warning:    %s\n",capi.getNotes());
		}
		out.printf("Challenge:  %s\n",capi.getChallenge());
		out.printf("PrivateKey:\n%s\n",capi.getPrivatekey());
		out.println("Certificate Chain:");
		for(String c : capi.getCerts()) {
			out.println(c);
		}
		return true;
	}
}
