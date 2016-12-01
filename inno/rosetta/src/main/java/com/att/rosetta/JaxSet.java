/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlType;

/**
 * For specific XML class, quickly find a Setter Method which will load the object
 * 
 * Object type of Setter must match String at this time.
 * 
 *
 * @param <T>
 */
public class JaxSet<T> {
	private static Map<Class<?>,JaxSet<?>> jsets = new HashMap<Class<?>,JaxSet<?>>();
	private Map<String,Setter<T>> members;

	private JaxSet(Class<?> cls) {
		members = new TreeMap<String, Setter<T>>();
		XmlType xmltype = cls.getAnnotation(XmlType.class);
		Class<?> paramType[] = new Class[] {String.class};
		for(String str : xmltype.propOrder()) {
			try {
				String setName = "set" + Character.toUpperCase(str.charAt(0)) + str.subSequence(1, str.length());
				Method meth = cls.getMethod(setName,paramType );
				if(meth!=null) {
					members.put(str, new Setter<T>(meth) {
						public void set(T o, Object t) throws ParseException {
							try {
								this.meth.invoke(o, t);
							} catch (Exception e) {
								throw new ParseException(e);
							}
						}
					});
				}
			} catch (Exception e) {
				// oops
			}
		}
	}
	
	public static abstract class Setter<O> {
		protected final Method meth;
		public Setter(Method meth) {
			this.meth = meth;
		}
		public abstract void set(O o, Object obj) throws ParseException;
	}

	public static <X> JaxSet<X> get(Class<?> cls) {
		synchronized(jsets) {
			@SuppressWarnings("unchecked")
			JaxSet<X> js = (JaxSet<X>)jsets.get(cls);
			if(js == null) {
				jsets.put(cls, js = new JaxSet<X>(cls));
			}
			return js;
		}
	}

	public Setter<T> get(String key) {
		return members.get(key);
	}
}
