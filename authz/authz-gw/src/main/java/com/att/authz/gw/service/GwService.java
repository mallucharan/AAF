/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.gw.service;

import com.att.authz.gw.mapper.Mapper;

public interface GwService<IN,OUT,ERROR> {
	public Mapper<IN,OUT,ERROR> mapper();
}
