/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.client;

import java.security.Principal;

import com.att.cadi.SecuritySetter;
import com.att.cadi.config.SecurityInfoC;
import com.att.cadi.principal.BasicPrincipal;
import com.att.cadi.principal.TGuardPrincipal;
import com.att.cadi.principal.TrustPrincipal;

public abstract class AbsTransferSS<CLIENT> implements SecuritySetter<CLIENT> {
	protected String value;
	protected SecurityInfoC<CLIENT> securityInfo;
	protected SecuritySetter<CLIENT> defSS;
	private Principal principal;

	//Format:<ID>:<APP>:<protocol>[:AS][,<ID>:<APP>:<protocol>]*
	public AbsTransferSS(Principal principal, String app) {
		init(principal, app);
	}

	public AbsTransferSS(Principal principal, String app, SecurityInfoC<CLIENT> si) {
		init(principal,app);
		securityInfo = si;
		this.defSS = si.defSS;
	}

	private void init(Principal principal, String app)  {
		this.principal=principal;
		if(principal==null) {
			return;
		} else if(principal instanceof BasicPrincipal) {
			value = principal.getName() + ':' + app + ":BasicAuth:AS";
		} else if(principal instanceof TrustPrincipal) {
			TrustPrincipal tp = (TrustPrincipal)principal;
			// recursive
			init(tp.original(),app);
			value += principal.getName() + ':' + app + ":Trust:AS" + ',' + tp.userChain();
		} else if(principal instanceof TGuardPrincipal) {
			value = principal.getName() + ':' + app + ":TGUARD:AS";
		}
	}

	/* (non-Javadoc)
	 * @see com.att.cadi.SecuritySetter#getID()
	 */
	@Override
	public String getID() {
		return principal==null?"":principal.getName();
	}
}
