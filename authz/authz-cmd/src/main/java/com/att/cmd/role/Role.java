/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.role;

import com.att.cmd.AAFcli;
import com.att.cmd.BaseCmd;
import com.att.inno.env.APIException;

public class Role extends BaseCmd<Role> {
	public List list;

	public Role(AAFcli aafcli) throws APIException {
		super(aafcli, "role");
		cmds.add(new CreateDelete(this));
//		cmds.add(new Delete(this));
		cmds.add(new User(this));
		cmds.add(new Describe(this));
		cmds.add(list = new List(this));
	}
}
