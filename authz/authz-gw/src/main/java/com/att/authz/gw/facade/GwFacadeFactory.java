/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.gw.facade;

import com.att.authz.env.AuthzEnv;
import com.att.authz.env.AuthzTrans;
import com.att.authz.gw.mapper.Mapper_1_0;
import com.att.authz.gw.service.GwServiceImpl;
import com.att.inno.env.APIException;
import com.att.inno.env.Data;

import gw.v1_0.Error;
import gw.v1_0.InRequest;
import gw.v1_0.Out;


public class GwFacadeFactory {
	public static GwFacade_1_0 v1_0(AuthzEnv env, AuthzTrans trans, Data.TYPE type) throws APIException {
		return new GwFacade_1_0(env,
				new GwServiceImpl<
					InRequest,
					Out,
					Error>(trans,new Mapper_1_0()),
				type);  
	}

}
