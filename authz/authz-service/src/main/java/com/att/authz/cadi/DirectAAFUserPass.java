/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cadi;

import static com.att.authz.layer.Result.OK;

import java.util.Date;

import com.att.authz.env.AuthzEnv;
import com.att.authz.env.AuthzTrans;
import com.att.authz.layer.Result;
import com.att.cadi.CredVal;
import com.att.dao.DAOException;
import com.att.dao.aaf.hl.Question;

/**
 * DirectAAFUserPass is intended to provide password Validation directly from Cassandra Database, and is only
 * intended for use in AAF itself.  The normal "AAF Taf" objects are, of course, clients.
 * 
 *
 */
public class DirectAAFUserPass implements CredVal {
		private final AuthzEnv env;
	private final Question question;
	
	public DirectAAFUserPass(AuthzEnv env, Question question, String appPass) {
		this.env = env;
		this.question = question;
	}
	
	@Override
	public boolean validate(String user, Type type, byte[] pass) {
		try {
			AuthzTrans trans = env.newTransNoAvg();
			Result<Date> result = question.doesUserCredMatch(trans, user, pass);
			trans.logAuditTrail(env.info());
			switch(result.status) {
				case OK:
					return true;
				default:
					env.warn().log(user, "failed Password Validation:",result.errorString());
			}
		} catch (DAOException e) {
			env.error().log(e,"Cannot validate User/Pass from Cassandra");
		}
		return false;
	}


}
