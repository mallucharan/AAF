/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.impl;

import com.att.inno.env.Decryptor;
import com.att.inno.env.Encryptor;
import com.att.inno.env.EnvJAXB;
import com.att.inno.env.Slot;
import com.att.inno.env.StaticSlot;
import com.att.inno.env.TimeTaken;


public class BasicTrans extends AbsTransJAXB {
	
	public BasicTrans(EnvJAXB env) {
		super(env);
	}

	@Override
	protected TimeTaken newTimeTaken(String name, int flag) {
		/**
		 * Note: could have created a different format for Time Taken, but using BasicEnv's instead
		 */
		return delegate.start(name, flag);
	}
	
	public Slot slot(String name) {
		return delegate.slot(name);
	}

	public <T> T get(StaticSlot slot) {
		return delegate.get(slot);
	}

	public <T> T get(StaticSlot slot, T dflt) {
		return delegate.get(slot,dflt);
	}

	public String setProperty(String tag, String value) {
		delegate.setProperty(tag, value);
		return value;
	}

	public String getProperty(String tag) {
		return delegate.getProperty(tag);
	}

	public String getProperty(String tag, String deflt) {
		return delegate.getProperty(tag, deflt);
	}

	@Override
	public Decryptor decryptor() {
		return delegate.decryptor();
	}

	@Override
	public Encryptor encryptor() {
		return delegate.encryptor();
	}

}
