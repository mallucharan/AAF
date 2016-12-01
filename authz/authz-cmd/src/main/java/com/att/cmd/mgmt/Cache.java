/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.mgmt;

import com.att.cmd.BaseCmd;
import com.att.inno.env.APIException;

public class Cache extends BaseCmd<Mgmt> {
	public Cache(Mgmt mgmt) throws APIException {
		super(mgmt, "cache");
		cmds.add(new Clear(this));
	}
}
