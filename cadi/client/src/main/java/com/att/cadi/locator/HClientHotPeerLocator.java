/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.locator;

import java.net.URI;
import java.net.URISyntaxException;

import com.att.cadi.Access;
import com.att.cadi.LocatorException;
import com.att.cadi.http.HClient;
import com.att.cadi.http.HX509SS;

public class HClientHotPeerLocator extends HotPeerLocator<HClient> {
	private final HX509SS ss;

	public HClientHotPeerLocator(Access access, String urlstr, long invalidateTime, String localLatitude,
			String localLongitude, HX509SS ss) throws LocatorException {
		super(access, urlstr, invalidateTime, localLatitude, localLongitude);
		
		this.ss = ss;
	}

	@Override
	protected HClient _newClient(String clientInfo) throws LocatorException {
		try {
			int idx = clientInfo.indexOf('/');
			return new HClient(ss,new URI("https://"+(idx<0?clientInfo:clientInfo.substring(0, idx))),3000);
		} catch (URISyntaxException e) {
			throw new LocatorException(e);
		}
	}

	@Override
	protected HClient _invalidate(HClient client) {
		return null;
	}

	@Override
	protected void _destroy(HClient client) {
	}
}