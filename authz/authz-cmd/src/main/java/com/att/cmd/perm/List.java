/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd.perm;

import java.util.ArrayList;
import java.util.Comparator;

import com.att.cadi.CadiException;
import com.att.cadi.client.Future;
import com.att.cadi.client.Rcli;
import com.att.cadi.client.Retryable;
import com.att.cmd.AAFcli;
import com.att.cmd.BaseCmd;
import com.att.inno.env.APIException;

import aaf.v2_0.Nss;
import aaf.v2_0.Perms;
import aaf.v2_0.Pkey;
import edu.emory.mathcs.backport.java.util.Collections;

public class List extends BaseCmd<Perm> {
//	private static final String LIST_PERM_DETAILS = "list permission details";
	
	public List(Perm parent) {
		super(parent,"list");

		cmds.add(new ListByUser(this));
		cmds.add(new ListByName(this));
		cmds.add(new ListByNS(this));
		cmds.add(new ListByRole(this));
		cmds.add(new ListActivity(this));
	}
	// Package Level on purpose
	abstract class ListPerms extends Retryable<Integer> {
		protected int list(Future<Perms> fp,Rcli<?> client, String header, String parentPerm) throws CadiException, APIException  {
			if(fp.get(AAFcli.timeout())) {	
				ArrayList<String> permNss = null;
				if (aafcli.isDetailed()) {
					permNss = new ArrayList<String>();
					String permNs = null;
					for(Pkey perm : fp.value.getPerm()) {	
						if (permNs != null && perm.getType().contains(permNs)) {
						    permNss.add(permNs);
						} else {
							Future<Nss> fpn = null;
							String permType = perm.getType();
							permNs = permType;
							do {
								permNs = permType.substring(0,permNs.lastIndexOf('.'));
								fpn = client.read("/authz/nss/"+permNs,getDF(Nss.class));
							} while (!fpn.get(AAFcli.timeout()));
							permNss.add(permNs);
						}
					}						
				} 
				report(fp,permNss,header, parentPerm);
			} else {
				error(fp);
			}
			return fp.code();
		}
	}

	private static final Comparator<aaf.v2_0.Perm> permCompare = new Comparator<aaf.v2_0.Perm>() {
		@Override
		public int compare(aaf.v2_0.Perm a, aaf.v2_0.Perm b) {
			int rc;
			if((rc=a.getType().compareTo(b.getType()))!=0) {
			    return rc;
			}
			if((rc=a.getInstance().compareTo(b.getInstance()))!=0) {
			    return rc;
			}
			return a.getAction().compareTo(b.getAction());
		}
	};
	
	void report(Future<Perms> fp, ArrayList<String> permNss, String ... str) {
		reportHead(str);
		if (this.aafcli.isDetailed()) {		
			String format = reportColHead("%-20s %-15s %-30s %-15s\n   %-75s\n","PERM NS","Type","Instance","Action", "Description");
			Collections.sort(fp.value.getPerm(),permCompare);
			for(aaf.v2_0.Perm p : fp.value.getPerm()) {
				String permNs = permNss.remove(0);
				pw().format(format,
					permNs,
					p.getType().substring(permNs.length()+1),
					p.getInstance(),
					p.getAction(),
					p.getDescription()==null?"":p.getDescription());
			}
			pw().println();
		} else {
			String format = reportColHead("%-30s %-30s %-10s\n","PERM Type","Instance","Action");

			Collections.sort(fp.value.getPerm(),permCompare);
			for(aaf.v2_0.Perm p : fp.value.getPerm()) {
				pw().format(format,
					p.getType(),
					p.getInstance(),
					p.getAction());
			}
			pw().println();
		}
	}

}
