/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.aaf;

import java.security.Principal;
import java.util.regex.Pattern;

import com.att.cadi.Transmutate;
import com.att.cadi.lur.ConfigPrincipal;
import com.att.cadi.principal.BasicPrincipal;
import com.att.cadi.principal.CSPPrincipal_T;

/**
 * AAFTransmutate
 * 
 * Each System determines the mechanisms for which one Principal is transmutated to another, such as whether it is created
 * independently, etc.
 * 
 * For AAF, the only important thing is that these are valid ATTUID/mechIDs, to avoid unnecessary user hits
 * 
 * attUIDs look like ab1234 or AB1234 or AZ123a
 * mechids look like m12345
 * 
 *
 */
public final class AAFTransmutate implements Transmutate<Principal> {
	private Pattern pattern = Pattern.compile("[a-zA-Z]\\w\\d\\d\\d\\w");

	public Principal mutate(Principal p) {
		// Accept these three internal kinds of Principals
		if(p instanceof CSPPrincipal_T 
			|| p instanceof BasicPrincipal
			|| p instanceof ConfigPrincipal) { 
			return p;
		} else { 
			
			final String name = p.getName();
			final int idx = name.indexOf('@');
			String shortName;
			if(idx>0) { // strip off any domain
				shortName = name.substring(0,idx); 
			} else {
				shortName = name;
			}

			// Check for ATTUID specs before creating CSP_T
			return pattern.matcher(shortName).matches()?
				new CSP_T(name): // Note: use REAL name, short name for CSP_T
				null;
		}
	}

	/**
	 * Essential Principal reflecting CSP Principal
	 * 
	 *
	 */
	private final class CSP_T implements CSPPrincipal_T {
		private String name;
		public CSP_T(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
}