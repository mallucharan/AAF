/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.lur;

import java.security.Principal;
import java.util.List;

import com.att.cadi.Lur;
import com.att.cadi.Permission;

public class NullLur implements Lur {
	private static final Permission NULL = new Permission() {
		@Override
		public String permType() {
			return "";
		}

		@Override
		public String getKey() {
			return "";
		}

		@Override
		public boolean match(Permission p) {
			return false;
		}};

	public boolean fish(Principal bait, Permission pond) {
		// Well, for Jenkins, this is ok... It finds out it can't do J2EE Security, and then looks at it's own
//		System.err.println("CADI's LUR has not been configured, but is still being called.  Access is being denied");
		return false;
	}

	public void fishAll(Principal bait,	List<Permission> permissions) {
	}

	public void destroy() {
	}

	public boolean handlesExclusively(Permission pond) {
		return false;
	}

	public boolean supports(String userName) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.att.cadi.Lur#createPerm(java.lang.String)
	 */
	@Override
	public Permission createPerm(String p) {
		return NULL;
	}

	/* (non-Javadoc)
	 * @see com.att.cadi.Lur#clear(java.security.Principal, java.lang.StringBuilder)
	 */
	@Override
	public void clear(Principal p, StringBuilder report) {
		report.append(NullLur.class.getSimpleName());
		report.append('\n');
	}
	
	public String toString() {
		return NullLur.class.getSimpleName() + '\n';
	}
}