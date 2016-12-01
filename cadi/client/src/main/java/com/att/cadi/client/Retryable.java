/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.client;

import java.net.ConnectException;

import com.att.cadi.CadiException;
import com.att.cadi.Locator;
import com.att.inno.env.APIException;

/**
 * 
 *
 * @param <RT>
 * @param <RET>
 */
public abstract class Retryable<RET> {
	// be able to hold state for consistent Connections.  Not required for all connection types.
	public Rcli<?> lastClient;
	private Locator.Item item;
	
	public Retryable() {
		lastClient = null;
		item = null;
	}

	public Retryable(Retryable<?> ret) {
		lastClient = ret.lastClient;
		item = ret.item;
	}

	public Locator.Item item(Locator.Item item) {
		lastClient = null;
		this.item = item;
		return item;
	}
	public Locator.Item item() {
		return item;
	}
	
	public abstract RET code(Rcli<?> client) throws CadiException, ConnectException, APIException;

	/**
	 * Note, Retryable is tightly coupled to the Client Utilizing.  It will not be the wrong type.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <CLIENT> Rcli<CLIENT> lastClient() {
		return (Rcli<CLIENT>)lastClient;
	}
}
