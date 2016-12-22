package com.att.authz.common;

import com.att.cadi.CadiException;
import com.att.cadi.config.Config;
import com.att.inno.env.Env;

public class Define {
	public static String ROOT_NS="NS.Not.Set";
	public static String ROOT_COMPANY=ROOT_NS;

	public static void set(Env env) throws CadiException {
		ROOT_NS = env.getProperty(Config.AAF_ROOT_NS);
		if(ROOT_NS==null) {
			throw new CadiException(Config.AAF_ROOT_NS + " property is required.");
		}
		ROOT_COMPANY = env.getProperty(Config.AAF_ROOT_COMPANY);
		if(ROOT_COMPANY==null) {
			int last = ROOT_NS.lastIndexOf('.');
			if(last>=0) {
				ROOT_COMPANY = ROOT_NS.substring(0, last);
			} else {
				throw new CadiException(Config.AAF_ROOT_COMPANY + " or " + Config.AAF_ROOT_NS + " property with 3 positions is required.");
			}
		}
		env.init().log("AAF Root NS is " + ROOT_NS + ", and AAF Root Company is " +ROOT_COMPANY);
	}
	
}
