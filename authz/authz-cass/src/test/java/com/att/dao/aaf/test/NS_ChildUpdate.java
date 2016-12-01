/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.test;

import com.att.authz.env.AuthzEnv;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class NS_ChildUpdate {

	public static void main(String[] args) {
		if(args.length < 3 ) {
			System.out.println("usage: NS_ChildUpdate machine mechid (encrypted)passwd");
		} else {
			try {
				AuthzEnv env = new AuthzEnv();
				env.setLog4JNames("log.properties","authz","authz","audit","init","trace");
				
				Cluster cluster = Cluster.builder()
						.addContactPoint(args[0])
						.withCredentials(args[1],env.decrypt(args[2], false))
						.build();
	
				Session session = cluster.connect("authz");
				try {
					ResultSet result = session.execute("SELECT name,parent FROM ns");
					int count = 0;
					for(Row r : result.all()) {
						++count;
						String name = r.getString(0);
						String parent = r.getString(1);
						if(parent==null) {
							int idx = name.lastIndexOf('.');
							
							parent = idx>0?name.substring(0, idx):".";
							System.out.println("UPDATE " + name + " to " + parent);
							session.execute("UPDATE ns SET parent='" + parent + "' WHERE name='" + name + "';");
						}
					}
					System.out.println("Processed " + count + " records");
				} finally {
					session.close();
					cluster.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
