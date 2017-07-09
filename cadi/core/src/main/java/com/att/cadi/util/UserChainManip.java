/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.util;

import com.att.cadi.UserChain;

public class UserChainManip {
	/** 
	    Build an element in the correct format for UserChain.
		Format:<APP>:<ID>:<protocol>[:AS][,<APP>:<ID>:<protocol>]*
		@see UserChain
	*/ 
	public static StringBuilder build(StringBuilder sb, String app, String id, UserChain.Protocol proto, boolean as) {
		boolean mayAs;
		if(!(mayAs=sb.length()==0)) {
			sb.append(',');
		}
		sb.append(app);
		sb.append(':');
		sb.append(id);
		sb.append(':');
		sb.append(proto.name());
		if(as && mayAs) {
			sb.append(":AS");
		}
		return sb;
	}
	
	public static String idToNS(String id) {
		if(id==null) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();
			char c;
			int end;
			boolean first = true;
			for(int idx = end = id.length()-1;idx>=0;--idx) {
				if((c = id.charAt(idx))=='@' || c=='.')  {
					if(idx<end) {
						if(first) {
							first = false;
						} else {
							sb.append('.');
						}
						for(int i=idx+1;i<=end;++i) {
							sb.append(id.charAt(i));
						}
					}
					end=idx-1;
					if(c=='@') {
						break;
					}
				}
			}
			return sb.toString();
		}
	}
}
