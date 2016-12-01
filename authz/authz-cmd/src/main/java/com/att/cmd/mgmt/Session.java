/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.mgmt;

import com.att.cmd.BaseCmd;
import com.att.inno.env.APIException;

public class Session extends BaseCmd<Mgmt> {
	public Session(Mgmt mgmt) throws APIException {
		super(mgmt, "dbsession");
		cmds.add(new SessClear(this));
	}
}
