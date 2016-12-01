/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.gw.mapper;

import com.att.cadi.util.Vars;

import gw.v1_0.Error;
import gw.v1_0.InRequest;
import gw.v1_0.Out;

public class Mapper_1_0 implements Mapper<InRequest,Out,Error> {
	
	@Override
	public Class<?> getClass(API api) {
		switch(api) {
			case IN_REQ: return InRequest.class;
			case OUT: return Out.class;
			case ERROR: return Error.class;
			case VOID: return Void.class;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> A newInstance(API api) {
		switch(api) {
			case IN_REQ: return (A) new InRequest();
			case OUT: return (A) new Out();
			case ERROR: return (A)new Error();
			case VOID: return null;
		}
		return null;
	}

	//////////////  Mapping Functions /////////////
	@Override
	public gw.v1_0.Error errorFromMessage(StringBuilder holder, String msgID, String text,String... var) {
		Error err = new Error();
		err.setMessageId(msgID);
		// AT&T Restful Error Format requires numbers "%" placements
		err.setText(Vars.convert(holder, text, var));
		for(String s : var) {
			err.getVariables().add(s);
		}
		return err;
	}

}