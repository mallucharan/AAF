/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd;

import com.att.aft.dme2.internal.jetty.http.HttpStatus;
import com.att.cadi.CadiException;
import com.att.cadi.LocatorException;
import com.att.cadi.config.Config;
import com.att.inno.env.APIException;

public class Version extends Cmd {


	public Version(AAFcli aafcli) {
		super(aafcli, "--version");
	}

	@Override
	protected int _exec(int idx, String... args) throws CadiException, APIException, LocatorException {
		pw().println("AAF Command Line Tool");
		String version = this.env().getProperty(Config.AAF_DEPLOYED_VERSION, "N/A");
		pw().println("Version: " + version);
		return HttpStatus.OK_200;
	}
}
