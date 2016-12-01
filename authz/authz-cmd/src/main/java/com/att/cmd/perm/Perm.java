/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.perm;

import com.att.cmd.BaseCmd;
import com.att.cmd.role.Role;
import com.att.inno.env.APIException;

public class Perm extends BaseCmd<Perm> {
	Role role;

	public Perm(Role role) throws APIException {
		super(role.aafcli, "perm");
		this.role = role;

		cmds.add(new Create(this));
		cmds.add(new Delete(this));
		cmds.add(new Grant(this));
		cmds.add(new Rename(this));
		cmds.add(new Describe(this));
		cmds.add(new List(this));
	}
}
