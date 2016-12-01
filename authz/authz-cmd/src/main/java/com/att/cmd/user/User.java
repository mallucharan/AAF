/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.user;

import com.att.cmd.AAFcli;
import com.att.cmd.BaseCmd;
import com.att.inno.env.APIException;

public class User extends BaseCmd<User> {
	public User(AAFcli aafcli) throws APIException {
		super(aafcli,"user");
		cmds.add(new Role(this));
		cmds.add(new Cred(this));
		cmds.add(new Delg(this));
		cmds.add(new List(this));
	}
}
