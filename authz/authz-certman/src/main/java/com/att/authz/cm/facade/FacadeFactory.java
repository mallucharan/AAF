/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cm.facade;

import com.att.authz.cm.mapper.Mapper1_0;
import com.att.authz.cm.service.CertManAPI;
import com.att.authz.cm.service.CMService;
import com.att.authz.env.AuthzTrans;
import com.att.inno.env.APIException;
import com.att.inno.env.Data;


public class FacadeFactory {
	public static Facade1_0 v1_0(CertManAPI certman, AuthzTrans trans, CMService service, Data.TYPE type) throws APIException {
		return new Facade1_0(
				certman,
				service,
				new Mapper1_0(),
				type);  
	}

}
