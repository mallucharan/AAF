/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.facade;

import com.att.authz.env.AuthzEnv;
import com.att.authz.service.AuthzService;
import com.att.inno.env.APIException;
import com.att.inno.env.Data;

import aaf.v2_0.Approvals;
import aaf.v2_0.Certs;
import aaf.v2_0.Delgs;
import aaf.v2_0.Error;
import aaf.v2_0.History;
import aaf.v2_0.Keys;
import aaf.v2_0.Nss;
import aaf.v2_0.Perms;
import aaf.v2_0.Pkey;
import aaf.v2_0.Request;
import aaf.v2_0.Roles;
import aaf.v2_0.UserRoles;
import aaf.v2_0.Users;

public class AuthzFacade_2_0 extends AuthzFacadeImpl<
	Nss,
	Perms,
	Pkey,
	Roles,
	Users,
	UserRoles,
	Delgs,
	Certs,
	Keys,
	Request,
	History,
	Error,
	Approvals>
{
	public AuthzFacade_2_0(AuthzEnv env,
			AuthzService<Nss, Perms, Pkey, Roles, Users, UserRoles, Delgs, Certs, Keys, Request, History, Error, Approvals> service,
			Data.TYPE type) throws APIException {
		super(env, service, type);
	}
}
