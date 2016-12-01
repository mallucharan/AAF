/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;


public interface Decryptor {
	public String decrypt(String tag);
	
	public static final Decryptor NULL = new Decryptor() {
		@Override
		public String decrypt(String tag) {
			return tag;
		}
	};
}
