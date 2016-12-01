/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cm.facade;

import com.att.authz.cm.mapper.Mapper;
import com.att.authz.cm.service.CMService;
import com.att.authz.cm.service.CertManAPI;
import com.att.inno.env.APIException;
import com.att.inno.env.Data;

import aaf.v2_0.Error;
import certman.v1_0.Artifacts;
import certman.v1_0.BaseRequest;
import certman.v1_0.CertInfo;

/**
 *
 */
public class Facade1_0 extends FacadeImpl<BaseRequest,CertInfo, Artifacts, Error> {
	public Facade1_0(CertManAPI certman, 
					 CMService service, 
					 Mapper<BaseRequest,CertInfo,Artifacts,Error> mapper, 
					 Data.TYPE type) throws APIException {
		super(certman, service, mapper, type);
	}
}
