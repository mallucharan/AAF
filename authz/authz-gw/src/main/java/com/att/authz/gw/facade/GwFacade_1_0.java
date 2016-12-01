/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.gw.facade;

import com.att.authz.env.AuthzEnv;
import com.att.authz.gw.service.GwService;
import com.att.inno.env.APIException;
import com.att.inno.env.Data;

import gw.v1_0.Error;
import gw.v1_0.InRequest;
import gw.v1_0.Out;

public class GwFacade_1_0 extends GwFacadeImpl<InRequest,Out,Error>
{
	public GwFacade_1_0(AuthzEnv env, GwService<InRequest,Out,Error> service, Data.TYPE type) throws APIException {
		super(env, service, type);
	}
}
