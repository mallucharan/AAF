/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.cadi;

import static com.att.authz.layer.Result.OK;

import java.security.Principal;
import java.util.List;

import com.att.authz.env.AuthzEnv;
import com.att.authz.env.AuthzTrans;
import com.att.authz.layer.Result;
import com.att.cadi.Lur;
import com.att.cadi.Permission;
import com.att.dao.aaf.cass.PermDAO;
import com.att.dao.aaf.cass.PermDAO.Data;
import com.att.dao.aaf.hl.Question;

public class DirectAAFLur implements Lur {
	private final AuthzEnv env;
	private final Question question;
	
	public DirectAAFLur(AuthzEnv env, Question question) {
		this.env = env;
		this.question = question;
	}

	@Override
	public boolean fish(Principal bait, Permission pond) {
		return fish(env.newTransNoAvg(),bait,pond);
	}
	
	public boolean fish(AuthzTrans trans, Principal bait, Permission pond) {
		Result<List<Data>> pdr = question.getPermsByUser(trans, bait.getName(),false);
		switch(pdr.status) {
			case OK:
				for(PermDAO.Data d : pdr.value) {
					if(new PermPermission(d).match(pond)) return true;
				}
				break;
			default:
				trans.error().log("Can't access Cassandra to fulfill Permission Query: ",pdr.status,"-",pdr.details);
		}
		return false;
	}

	@Override
	public void fishAll(Principal bait, List<Permission> permissions) {
		Result<List<Data>> pdr = question.getPermsByUser(env.newTrans(), bait.getName(),false);
		switch(pdr.status) {
			case OK:
				for(PermDAO.Data d : pdr.value) {
					permissions.add(new PermPermission(d));
				}
				break;
			default:
				env.error().log("Can't access Cassandra to fulfill Permission Query: ",pdr.status,"-", pdr.details);
		}
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public boolean handlesExclusively(Permission pond) {
		return false;
	}
	
	/**
	 * Small Class implementing CADI's Permission with Cassandra Data
	 *
	 */
	public static class PermPermission implements Permission {
		private PermDAO.Data data;
		
		public PermPermission(PermDAO.Data d) {
			data = d;
		}
		
		public PermPermission(AuthzTrans trans, Question q, String p) {
			data = PermDAO.Data.create(trans, q, p);
		}
		
		public PermPermission(String ns, String type, String instance, String action) {
			data = new PermDAO.Data();
			data.ns = ns;
			data.type = type;
			data.instance = instance;
			data.action = action;
		}

		@Override
		public String getKey() {
			return data.type;
		}

		@Override
		public boolean match(Permission p) {
			if(p==null)return false;
			PermDAO.Data pd;
			if(p instanceof DirectAAFLur.PermPermission) {
				pd = ((DirectAAFLur.PermPermission)p).data;
				if(data.ns.equals(pd.ns))
					if(data.type.equals(pd.type))
						if(data.instance!=null && (data.instance.equals(pd.instance) || "*".equals(data.instance)))
							if(data.action!=null && (data.action.equals(pd.action) || "*".equals(data.action)))
								return true;
			} else{
				String[] lp = p.getKey().split("\\|");
				if(lp.length<3)return false;
				if(data.fullType().equals(lp[0]))
					if(data.instance!=null && (data.instance.equals(lp[1]) || "*".equals(data.instance)))
						if(data.action!=null && (data.action.equals(lp[2]) || "*".equals(data.action)))
							return true;
			}
			return false;
		}

		@Override
		public String permType() {
			return "AAFLUR";
		}
		
	}
	
	public String toString() {
		return "DirectAAFLur is enabled";
		
	}

	@Override
	public boolean supports(String userName) {
		//TODO
		return true;
	}

	@Override
	public Permission createPerm(String p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear(Principal p, StringBuilder report) {
		// TODO Auto-generated method stub
		
	}
}
