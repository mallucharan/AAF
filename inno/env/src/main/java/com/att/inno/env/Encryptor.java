/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;


public interface Encryptor {
	public String encrypt(String data);

	public static final Encryptor NULL = new Encryptor() {
		@Override
		public String encrypt(String data) {
			return data;
		}
	};
}
