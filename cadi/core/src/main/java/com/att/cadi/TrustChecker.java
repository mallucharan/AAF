/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi;


import javax.servlet.http.HttpServletRequest;

import com.att.cadi.taf.TafResp;

/**
 * Change to another Principal based on Trust of caller and User Chain (if desired)
 * 
 *
 */
public interface TrustChecker {
	public TafResp mayTrust(TafResp tresp, HttpServletRequest req);
	
	/**
	 * A class that trusts no-one else, so just return same TResp
	 */
	public static TrustChecker NOTRUST = new TrustChecker() {
		@Override
		public TafResp mayTrust(TafResp tresp, HttpServletRequest req) {
			return tresp;
		}

		@Override
		public void setLur(Lur lur) {
		}
	};

	public void setLur(Lur lur);
}
