/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.gw.mapper;

public interface Mapper<IN,OUT,ERROR>
{
	public enum API{IN_REQ,OUT,ERROR,VOID};
	public Class<?> getClass(API api);
	public<A> A newInstance(API api);

	public ERROR errorFromMessage(StringBuilder holder, String msgID, String text, String... detail);

}
