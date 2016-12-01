/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.hl;

import com.att.authz.env.AuthzTrans;
import com.att.authz.layer.Result;
import com.att.authz.org.Executor;
import com.att.dao.aaf.cass.NsDAO.Data;
import com.att.dao.aaf.cass.NsSplit;

public class CassExecutor implements Executor {

	private Question q;
	private Function f;
	private AuthzTrans trans;

	public CassExecutor(AuthzTrans trans, Function f) {
		this.trans = trans;
		this.f = f;
		this.q = this.f.q;
	}

	@Override
	public boolean hasPermission(String user, String ns, String type, String instance, String action) {
		return isGranted(user, ns, type, instance, action);
	}

	@Override
	public boolean inRole(String name) {
		Result<NsSplit> nss = q.deriveNsSplit(trans, name);
		if(nss.notOK())return false;
		return q.roleDAO.read(trans, nss.value.ns,nss.value.name).isOKhasData();
	}

	public boolean isGranted(String user, String ns, String type, String instance, String action) {
		return q.isGranted(trans, user, ns, type, instance,action);
	}

	@Override
	public String namespace() throws Exception {
		Result<Data> res = q.validNSOfDomain(trans,trans.user());
		if(res.isOK()) {
			String user[] = trans.user().split("\\.");
			return user[user.length-1] + '.' + user[user.length-2];
		}
		throw new Exception(res.status + ' ' + res.details);
	}

	@Override
	public String id() {
		return trans.user();
	}

}
