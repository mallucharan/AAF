/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.client;

import java.io.IOException;

import com.att.cadi.SecuritySetter;
import com.att.cadi.Symm;
import com.att.cadi.config.SecurityInfo;

public abstract class AbsBasicAuth<CLIENT> implements SecuritySetter<CLIENT> {
		protected final String headValue;
		protected SecurityInfo<CLIENT> securityInfo;
		protected String user;

		public AbsBasicAuth(String user, String pass, SecurityInfo<CLIENT> si) throws IOException {
			this.user = user;
			headValue = "Basic " + Symm.base64.encode(user + ':' + pass);
			securityInfo = si;
		}

		/* (non-Javadoc)
		 * @see com.att.cadi.SecuritySetter#getID()
		 */
		@Override
		public String getID() {
			return user;
		}

}
