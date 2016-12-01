/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen;


public interface Cache<G extends XGen<G>> {
	public void dynamic(G hgen, Code<G> code);
	
	public static class Null<N extends XGen<N>> implements Cache<N> {
		@Override
		public void dynamic(N hgen, Code<N> code) {} // NO_OP, no matter what type

		@SuppressWarnings("rawtypes")
		private static Null<?> singleton = new Null();
		public static Null<?> singleton() { return singleton;}
	}

}