/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cmd;

import java.util.ArrayList;
import java.util.List;

import com.att.cadi.CadiException;
import com.att.cadi.LocatorException;
import com.att.inno.env.APIException;


public class BaseCmd<CMD extends Cmd> extends Cmd  {
	protected List<Cmd> 	cmds;

	public BaseCmd(AAFcli aafcli, String name, Param ... params) {
		super(aafcli, null, name, params);
		cmds = new ArrayList<Cmd>();
	}
	
	public BaseCmd(CMD parent, String name, Param ... params) {
		super(parent.aafcli, parent, name, params);
		cmds = new ArrayList<Cmd>();
	}

	
	@Override
	public int _exec( int idx, final String ... args) throws CadiException, APIException, LocatorException {
		if(args.length-idx<1) {
			pw().println(build(new StringBuilder(),null).toString());
		} else {
			String s = args[idx];
			String name;
			Cmd empty = null;
			for(Cmd c: cmds) {
				name = c.getName();
				if(name==null && empty==null) { // Mark with Command is null, and take the first one.  
					empty = c;
				} else if(s.equalsIgnoreCase(c.getName()))
					return c.exec(idx+1, args);
			}
			if(empty!=null) {
				return empty.exec(idx, args); // If name is null, don't account for it on command line.  jg 4-29
			}
			pw().println("Instructions not understood.");
		}
		return 0;
	}
}
