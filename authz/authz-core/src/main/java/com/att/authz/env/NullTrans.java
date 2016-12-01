/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.env;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.att.authz.org.Organization;
import com.att.cadi.Lur;
import com.att.cadi.Permission;
import com.att.inno.env.Decryptor;
import com.att.inno.env.Encryptor;
import com.att.inno.env.LogTarget;
import com.att.inno.env.Slot;
import com.att.inno.env.StaticSlot;
import com.att.inno.env.TimeTaken;

/**
 * A NULL implementation of AuthzTrans, for use in DirectAAF Taf/Lurs
 */
public class NullTrans implements AuthzTrans {
	private static final AuthzTrans singleton = new NullTrans();
	
	public static final AuthzTrans singleton() {
		return singleton;
	}
	
	public void checkpoint(String text) {}
	public void checkpoint(String text, int additionalFlag) {}
	public Metric auditTrail(int indent, StringBuilder sb, int... flag) {return null;}
	public LogTarget fatal() {
		return LogTarget.NULL;
	}

	public LogTarget error() {
		return LogTarget.NULL;
	}

	public LogTarget audit() {
		return LogTarget.NULL;
	}

	/* (non-Javadoc)
	 * @see com.att.env.Env#init()
	 */
	@Override
	public LogTarget init() {
		return LogTarget.NULL;
	}

	public LogTarget warn() {
		return LogTarget.NULL;
	}

	public LogTarget info() {
		return LogTarget.NULL;
	}

	public LogTarget debug() {
		return LogTarget.NULL;
	}

	public LogTarget trace() {
		return LogTarget.NULL;
	}

	public TimeTaken start(String name, int flag) {
		return new TimeTaken(name,flag) {
			public void output(StringBuilder sb) {
				sb.append(name);
				sb.append(' ');
				sb.append(millis());
				sb.append("ms");
			}
		};
	}

	@Override
	public String setProperty(String tag, String value) {
		return value;
	}

	@Override
	public String getProperty(String tag) {
		return tag;
	}

	@Override
	public String getProperty(String tag, String deflt) {
		return deflt;
	}

	@Override
	public Decryptor decryptor() {
		return null;
	}

	@Override
	public Encryptor encryptor() {
		return null;
	}
	@Override
	public AuthzTrans set(HttpServletRequest req) {
		return null;
	}

	@Override
	public String user() {
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		return null;
	}

	@Override
	public String ip() {
		return null;
	}

	@Override
	public int port() {
		return 0;
	}
	@Override
	public String meth() {
		return null;
	}

	@Override
	public String path() {
		return null;
	}

	@Override
	public void put(Slot slot, Object value) {
	}
	@Override
	public <T> T get(Slot slot, T deflt) {
		return null;
	}
	@Override
	public <T> T get(StaticSlot slot, T dflt) {
		return null;
	}
	@Override
	public void setUser(Principal p) {
	}
	@Override
	public Slot slot(String name) {
		return null;
	}
	@Override
	public AuthzEnv env() {
		return null;
	}
	@Override
	public String agent() {
		return null;
	}

	@Override
	public void setLur(Lur lur) {
	}

	@Override
	public boolean fish(Permission p) {
		return false;
	}

	@Override
	public boolean forceRequested() {
		return false;
	}

	@Override
	public boolean futureRequested() {
		return false;
	}

	@Override
	public boolean moveRequested() {
		return false;
	}

	@Override
	public Organization org() {
		return Organization.NULL;
	}

	@Override
	public void logAuditTrail(LogTarget lt) {
	}

}

