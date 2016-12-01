/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.facade;

import com.att.authz.env.AuthzEnv;
import com.att.authz.env.AuthzTrans;
import com.att.authz.service.AuthzCassServiceImpl;
import com.att.authz.service.mapper.Mapper_2_0;
import com.att.dao.aaf.hl.Question;
import com.att.inno.env.APIException;
import com.att.inno.env.Data;


public class AuthzFacadeFactory {
	public static AuthzFacade_2_0 v2_0(AuthzEnv env, AuthzTrans trans, Data.TYPE type, Question question) throws APIException {
		return new AuthzFacade_2_0(env,
				new AuthzCassServiceImpl<
					aaf.v2_0.Nss,
					aaf.v2_0.Perms,
					aaf.v2_0.Pkey,
					aaf.v2_0.Roles,
					aaf.v2_0.Users,
					aaf.v2_0.UserRoles,
					aaf.v2_0.Delgs,
					aaf.v2_0.Certs,
					aaf.v2_0.Keys,
					aaf.v2_0.Request,
					aaf.v2_0.History,
					aaf.v2_0.Error,
					aaf.v2_0.Approvals>
					(trans,new Mapper_2_0(question),question),
				type);
	}
	

}
