/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.mgmt;

import com.att.cmd.AAFcli;
import com.att.cmd.BaseCmd;
import com.att.inno.env.APIException;

public class Mgmt extends BaseCmd<Mgmt> {
	public Mgmt(AAFcli aafcli) throws APIException {
		super(aafcli, "mgmt");
		cmds.add(new Cache(this));
		cmds.add(new Deny(this));
		cmds.add(new Log(this));
		cmds.add(new Session(this));
	}
}
