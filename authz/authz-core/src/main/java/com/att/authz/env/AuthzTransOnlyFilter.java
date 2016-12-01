/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.env;

import java.security.Principal;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.att.cssa.rserv.TransOnlyFilter;
import com.att.inno.env.Env;
import com.att.inno.env.TimeTaken;
import com.att.inno.env.Trans.Metric;

public class AuthzTransOnlyFilter extends TransOnlyFilter<AuthzTrans> {
	private AuthzEnv env;
	public Metric serviceMetric;

	public static final int BUCKETSIZE = 2;

	public AuthzTransOnlyFilter(AuthzEnv env) {
		this.env = env;
		serviceMetric = new Metric();
		serviceMetric.buckets = new float[BUCKETSIZE]; 
	}
	
	@Override
	protected AuthzTrans newTrans() {
		return env.newTrans();
	}

	@Override
	protected TimeTaken start(AuthzTrans trans, ServletRequest request) {
		trans.set((HttpServletRequest)request);
		return trans.start("Trans " + //(context==null?"n/a":context.toString()) +
		" IP: " + trans.ip() +
		" Port: " + trans.port()
		, Env.SUB);
	}

	@Override
	protected void authenticated(AuthzTrans trans, Principal p) {
		trans.setUser(p);
	}

	@Override
	protected void tallyHo(AuthzTrans trans) {
		// Transaction is done, now post
		StringBuilder sb = new StringBuilder("AuditTrail\n");
		// We'll grab sub-metrics for Remote Calls and JSON
		// IMPORTANT!!! if you add more entries here, change "BUCKETSIZE"!!!
		Metric m = trans.auditTrail(1, sb, Env.REMOTE,Env.JSON);
		// Add current Metrics to total metrics
		serviceMetric.total+= m.total;
		for(int i=0;i<serviceMetric.buckets.length;++i) {
			serviceMetric.buckets[i]+=m.buckets[i];
		}
		// Log current info
		sb.append("  Total: ");
		sb.append(m.total);
		sb.append(" Remote: ");
		sb.append(m.buckets[0]);
		sb.append(" JSON: ");
		sb.append(m.buckets[1]);
		trans.info().log(sb);
	}

}
