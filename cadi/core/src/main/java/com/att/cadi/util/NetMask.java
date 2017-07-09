/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.util;

/* 
 * NetMask - a class to quickly validate whether a given IP is part of a mask, as defined by bytes or standard String format.
 * 
 * Needs the IPV6 Mask Builder. 
 */
public class NetMask {
	private long mask;

	public NetMask(byte[] inBytes) {
		mask = derive(inBytes);
	}
	
	public NetMask(String string) throws MaskFormatException {
		mask = derive(string,true);
	}
	
	public boolean isInNet(byte[] inBytes) {
		long addr = derive(inBytes);
		return (mask & addr) == addr;
	}
	
	public boolean isInNet(String str) {
		long addr;
		try {
			addr = derive(str,false);
			return (mask & addr) == addr;
		} catch (MaskFormatException e) {
			// will not hit this code;
			return false;
		}
	}

	public static long derive(byte[] inBytes) {
		long addr = 0L;
		int offset = inBytes.length*8;
		for(int i=0;i<inBytes.length;++i) {
			addr&=(inBytes[i]<<offset);
			offset-=8;
		}
		return addr;
	}

	public static long derive(String str, boolean check) throws MaskFormatException {
		long rv=0L;
		int idx=str.indexOf(':');
		int slash = str.indexOf('/');

		if(idx<0) { // Not IPV6, so it's IPV4... Is there a mask of 123/254?
			idx=str.indexOf('.');
			int offset = 24;
			int end = slash>=0?slash:str.length();
			int bits = slash>=0?Integer.parseInt(str.substring(slash+1)):32;
			if(check && bits>32) {
				throw new MaskFormatException("Invalid Mask Offset in IPV4 Address");
			}
			int prev = 0;
			long lbyte;
			while(prev<end) {
				if(idx<0) {
					idx = end;
				}
				lbyte = Long.parseLong(str.substring(prev, idx));
				if(check && (lbyte>255 || lbyte<0)) {
					throw new MaskFormatException("Invalid Byte in IPV4 Address");
				}
				rv|=lbyte<<offset;
				prev = ++idx;
				idx=str.indexOf('.',prev);
				offset-=8;
			}
			rv|=0x00000000FFFFFFFFL>>bits;
		}
		return rv;
	}

}
