/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi;


/**
 * UserPass
 * 
 * The essential interface required by BasicAuth to determine if a given User/Password combination is 
 * valid.  This is done as an interface.
 * 
 */
public interface CredVal {
	public enum Type{PASSWORD};
	/**
	 *  Validate if the User/Password combination matches records 
	 * @param user
	 * @param pass
	 * @return
	 */
	public boolean validate(String user, Type type, byte[] cred);
}
