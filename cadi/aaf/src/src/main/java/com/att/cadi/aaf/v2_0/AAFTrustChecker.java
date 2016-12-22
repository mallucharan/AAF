/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.aaf.v2_0;

import javax.servlet.http.HttpServletRequest ;

import com.att.cadi.Lur;
import com.att.cadi.TrustChecker;
import com.att.cadi.aaf.AAFPermission;
import com.att.cadi.principal.TrustPrincipal;
import com.att.cadi.taf.TafResp;
import com.att.cadi.taf.TrustNotTafResp;
import com.att.cadi.taf.TrustTafResp;
import com.att.inno.env.util.Split;

public class AAFTrustChecker implements TrustChecker {
	private final String tag,type,instance,action;
	private Lur lur;

	/**
	 * 
	 * Instance will be replaced by Identity
	 * @param lur 
	 *    
	 * @param tag
	 * @param perm
	 */
	public AAFTrustChecker(final String tag, final String perm) {
		this.tag = tag;
		String[] split = Split.split('|', perm);
		this.type = split[0];
		this.instance = split[1];
		this.action = split[2];
	}
	
	/* (non-Javadoc)
	 * @see com.att.cadi.TrustChecker#setLur(com.att.cadi.Lur)
	 */
	@Override
	public void setLur(Lur lur) {
		this.lur = lur;
	}

	@Override
	public TafResp mayTrust(TafResp tresp, HttpServletRequest req) {
		String user_info = req.getHeader(tag);
		if(user_info !=null ) {
			String[] info = Split.split(',', user_info);
			if(info.length>0) {
				String[] flds = Split.split(':',info[0]);
				if(flds.length>3 && "AS".equals(flds[3])) { // is it set for "AS"
					if(!tresp.getPrincipal().getName().equals(flds[0])) { // We do trust ourselves, if a trust entry is made with self
						if(lur.fish(tresp.getPrincipal(), new AAFPermission(type,instance,action))) {
							return new TrustTafResp(tresp,
									new TrustPrincipal(tresp.getPrincipal(), flds[0]),
									"  " + flds[0] + " validated using " + flds[2] + " by " + flds[1] + ','
								);
						} else {
							return new TrustNotTafResp(tresp, "  " + tresp.getPrincipal().getName() + 
									" requested identity change to " + flds[0] + ", but does not have Authorization");
						}
					}
				}
			}
		}

		return tresp;
	}

}