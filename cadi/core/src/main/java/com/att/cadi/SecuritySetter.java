/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi;


/**
 *  Apply any particular security mechanism
 *  
 *  This allows the definition of various mechanisms involved outside of DRcli jars 
 *  
 *
 */
public interface SecuritySetter<CT> {
	public String getID();
	
	public void setSecurity(CT client) throws CadiException;
	
	/**
	 * Returns number of bad logins registered
	 * @param respCode
	 * @return
	 */
	public int setLastResponse(int respCode);
}
