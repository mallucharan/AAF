/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.ns;

import javax.xml.datatype.XMLGregorianCalendar;

import com.att.cmd.BaseCmd;

import aaf.v2_0.Users.User;

public class ListUsers extends BaseCmd<List> {
	
	public ListUsers(List parent) {
		super(parent,"user");
		cmds.add(new ListUsersWithPerm(this));
		cmds.add(new ListUsersInRole(this));
	}

	public void report(String header, String ns) {
		((List)parent).report(null, header,ns);
	}

	public void report(String subHead) {
		pw().println(subHead);
	}

	private static final String uformat = "%s%-50s expires:%02d/%02d/%04d\n";
	public void report(String prefix, User u) {
		XMLGregorianCalendar xgc = u.getExpires();
		pw().format(uformat,prefix,u.getId(),xgc.getMonth()+1,xgc.getDay(),xgc.getYear());
	}

}
