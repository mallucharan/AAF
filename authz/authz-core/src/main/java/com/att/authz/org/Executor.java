/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.org;

public interface Executor {
	// remove User from user/Role
	// remove user from Admins
	// if # of Owners > 1, remove User from Owner
	// if # of Owners = 1, changeOwner to X  Remove Owner????
	boolean hasPermission(String user, String ns, String type, String instance, String action); 
	boolean inRole(String name);
	
	public String namespace() throws Exception;
	public String id();
}
