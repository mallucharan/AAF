/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.ns;

import com.att.cmd.AAFcli;
import com.att.cmd.BaseCmd;
import com.att.inno.env.APIException;

public class NS extends BaseCmd<NS> {
//	final Role role;

	public NS(AAFcli aafcli) throws APIException {
		super(aafcli, "ns");
//		this.role = role;
	
		cmds.add(new Create(this));
		cmds.add(new Delete(this));
		cmds.add(new Admin(this));
		cmds.add(new Responsible(this));
		cmds.add(new Describe(this));
		cmds.add(new Attrib(this));
		cmds.add(new List(this));
	}


}
