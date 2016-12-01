/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.cached;

import java.util.ArrayList;
import java.util.List;

import com.att.authz.env.AuthzTrans;
import com.att.authz.layer.Result;
import com.att.dao.CIDAO;
import com.att.dao.CachedDAO;
import com.att.dao.aaf.cass.Status;
import com.att.dao.aaf.cass.UserRoleDAO;
import com.att.dao.aaf.cass.UserRoleDAO.Data;
import com.att.inno.env.Slot;

public class CachedUserRoleDAO extends CachedDAO<AuthzTrans,UserRoleDAO, UserRoleDAO.Data> {
	private Slot transURSlot;

	public CachedUserRoleDAO(UserRoleDAO dao, CIDAO<AuthzTrans> info) {
		super(dao, info, UserRoleDAO.CACHE_SEG);
		transURSlot = dao.transURSlot;
	}

	/**
	 * Special Case.  
	 * User Roles by User are very likely to be called many times in a Transaction, to validate "May User do..."
	 * Pull result, and make accessible by the Trans, which is always keyed by User.
	 * @param trans
	 * @param user
	 * @return
	 */
	public Result<List<Data>> readByUser(AuthzTrans trans, final String user) {
		DAOGetter getter = new DAOGetter(trans,dao()) {
			public Result<List<Data>> call() {
				// If the call is for THIS user, and it exists, get from TRANS, add to TRANS if not.
				if(user!=null && user.equals(trans.user())) {
					Result<List<Data>> transLD = trans.get(transURSlot,null);
					if(transLD==null ) {
						transLD = dao.readByUser(trans, user);
					}
					return transLD;
				} else {
					return dao.readByUser(trans, user);
				}
			}
		};
		Result<List<Data>> lurd = get(trans, user, getter);
		if(lurd.isOK() && lurd.isEmpty()) {
			return Result.err(Status.ERR_UserRoleNotFound,"UserRole not found for [%s]",user);
		}
		return lurd;
	}

	
	public Result<List<Data>> readByRole(AuthzTrans trans, final String role) {
		DAOGetter getter = new DAOGetter(trans,dao()) {
			public Result<List<Data>> call() {
				return dao.readByRole(trans, role);
			}
		};
		Result<List<Data>> lurd = get(trans, role, getter);
		if(lurd.isOK() && lurd.isEmpty()) {
			return Result.err(Status.ERR_UserRoleNotFound,"UserRole not found for [%s]",role);
		}
		return lurd;
	}

	public Result<List<UserRoleDAO.Data>> readUserInRole(final AuthzTrans trans, final String user, final String role) {
		DAOGetter getter = new DAOGetter(trans,dao()) {
			public Result<List<Data>> call() {
				if(user.equals(trans.user())) {
					Result<List<Data>> rrbu = readByUser(trans, user);
					if(rrbu.isOK()) {
						List<Data> ld = new ArrayList<Data>(1);
						for(Data d : rrbu.value) {
							if(d.role.equals(role)) {
								ld.add(d);
								break;
							}
						}
						return Result.ok(ld).emptyList(ld.isEmpty());
					} else {
						return rrbu;
					}
				}
				return dao.readByUserRole(trans, user, role);
			}
		};
		Result<List<Data>> lurd = get(trans, keyFromObjs(user,role), getter);
		if(lurd.isOK() && lurd.isEmpty()) {
			return Result.err(Status.ERR_UserRoleNotFound,"UserRole not found for role [%s] and user [%s]",role,user);
		}
		return lurd;
	}
}
