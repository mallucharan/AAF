/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.util;

/**
 * Split by Char, optional Trim
 * 
 * Note: I read the String split and Pattern split code, and we can do this more efficiently for a single Character
 * 
 * jg1555 8/20/2015
 */

public class Split {
	  public static String[] split(char c, String value) {
		  // Count items to preallocate Array (memory alloc is more expensive than counting twice)
		  int count,idx;
		  for(count=1,idx=value.indexOf(c);idx>=0;idx=value.indexOf(c,++idx),++count);
		  String[] rv = new String[count];
		  if(count==1) {
			  rv[0]=value;
		  } else {
			  int last=0;
			  count=-1;
			  for(idx=value.indexOf(c);idx>=0;idx=value.indexOf(c,idx)) {
				  rv[++count]=value.substring(last,idx);
				  last = ++idx;
			  }
			  rv[++count]=value.substring(last);
		  }
		  return rv;
	  }

	  public static String[] splitTrim(char c, String value) {
		  // Count items to preallocate Array (memory alloc is more expensive than counting twice)
		  int count,idx;
		  for(count=1,idx=value.indexOf(c);idx>=0;idx=value.indexOf(c,++idx),++count);
		  String[] rv = new String[count];
		  if(count==1) {
			  rv[0]=value.trim();
		  } else {
			  int last=0;
			  count=-1;
			  for(idx=value.indexOf(c);idx>=0;idx=value.indexOf(c,idx)) {
				  rv[++count]=value.substring(last,idx).trim();
				  last = ++idx;
			  }
			  rv[++count]=value.substring(last).trim();
		  }
		  return rv;
	  }

	  public static String[] splitTrim(char c, String value, int size) {
		  int idx;
		  String[] rv = new String[size];
		  if(size==1) {
			  rv[0]=value.trim();
		  } else {
			  int last=0;
			  int count=-1;
			  size-=2;
			  for(idx=value.indexOf(c);idx>=0 && count<size;idx=value.indexOf(c,idx)) {
				  rv[++count]=value.substring(last,idx).trim();
				  last = ++idx;
			  }
			  rv[++count]=value.substring(last).trim();
		  }
		  return rv;
	  }

}
