/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.aaf.client;

import java.io.PrintStream;

import aaf.v2_0.Error;

import com.att.cadi.client.Future;
import com.att.cadi.util.Vars;
import com.att.inno.env.APIException;
import com.att.inno.env.Data.TYPE;
import com.att.rosetta.env.RosettaDF;
import com.att.rosetta.env.RosettaEnv;

public class ErrMessage {
	private RosettaDF<Error> errDF;
	
	public ErrMessage(RosettaEnv env) throws APIException {
		errDF = env.newDataFactory(Error.class);
	}

	/**
	 * AT&T Requires a specific Error Format for RESTful Services, which AAF complies with.
	 * 
	 * This code will create a meaningful string from this format. 
	 * 
	 * @param ps
	 * @param df
	 * @param r
	 * @throws APIException
	 */
	public void printErr(PrintStream ps,  String attErrJson) throws APIException {
		StringBuilder sb = new StringBuilder();
		Error err = errDF.newData().in(TYPE.JSON).load(attErrJson).asObject();
		ps.println(toMsg(sb,err));
	}
	
	/**
	 * AT&T Requires a specific Error Format for RESTful Services, which AAF complies with.
	 * 
	 * This code will create a meaningful string from this format. 
	 * 
	 * @param sb
	 * @param df
	 * @param r
	 * @throws APIException
	 */
	public StringBuilder toMsg(StringBuilder sb,  String attErrJson) throws APIException {
		return toMsg(sb,errDF.newData().in(TYPE.JSON).load(attErrJson).asObject());
	}
	
	public StringBuilder toMsg(Future<?> future) {
		return toMsg(new StringBuilder(),future);
	}
	
	public StringBuilder toMsg(StringBuilder sb, Future<?> future) {
		try {
			toMsg(sb,errDF.newData().in(TYPE.JSON).load(future.body()).asObject());
		} catch(Exception e) {
			//just print what we can
			sb.append(future.code());
			sb.append(": ");
			sb.append(future.body());
		}
		return sb;
	}

	public StringBuilder toMsg(StringBuilder sb, Error err) {
		sb.append(err.getMessageId());
		sb.append(' ');
		String[] vars = new String[err.getVariables().size()];
		err.getVariables().toArray(vars);
		Vars.convert(sb, err.getText(),vars);
		return sb;
	}
}
