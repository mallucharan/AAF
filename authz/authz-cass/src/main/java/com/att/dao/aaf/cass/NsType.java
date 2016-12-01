/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao.aaf.cass;

/**
 * Defines the Type Codes in the NS Table.
 *
 */
public enum NsType {
		UNKNOWN (-1),
		DOT (0),
		ROOT (1), 
		COMPANY (2), 
		APP (3), 
		STACKED_APP (10), 
		STACK (11);
		
		public final int type;
		private NsType(int t) {
			type = t;
		}
		/**
		 * This is not the Ordinal, but the Type that is stored in NS Tables
		 * 
		 * @param t
		 * @return
		 */
		public static NsType fromType(int t) {
			for(NsType nst : values()) {
				if(t==nst.type) {
					return nst;
				}
			}
			return UNKNOWN;
		}
		
		/**
		 * Use this one rather than "valueOf" to avoid Exception
		 * @param s
		 * @return
		 */
		public static NsType fromString(String s) {
			if(s!=null) {
				for(NsType nst : values()) {
					if(nst.name().equals(s)) {
						return nst;
					}
				}
			}
			return UNKNOWN;
		}

		
}
