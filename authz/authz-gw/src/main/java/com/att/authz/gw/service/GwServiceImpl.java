/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.gw.service;

import com.att.authz.env.AuthzTrans;
import com.att.authz.gw.mapper.Mapper;

public class GwServiceImpl<IN,OUT,ERROR> 
	  implements GwService<IN,OUT,ERROR> {
	
		private Mapper<IN,OUT,ERROR> mapper;
	
		public GwServiceImpl(AuthzTrans trans, Mapper<IN,OUT,ERROR> mapper) {
			this.mapper = mapper;
		}
		
		public Mapper<IN,OUT,ERROR> mapper() {return mapper;}

//////////////// APIs ///////////////////
};
