/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.util;

import java.io.IOException;
import java.io.OutputStream;

public class JsonOutputStream extends OutputStream {
	private static final byte[] TWO_SPACE = "  ".getBytes();
	private OutputStream os;
	private boolean closeable;
	private int indent = 0;
	private int prev,ret=0;

	public JsonOutputStream(OutputStream os) {
		// Don't close these, or dire consequences.
		closeable = !os.equals(System.out) && !os.equals(System.err);
		this.os = os;
	}

	@Override
	public void write(int b) throws IOException {
		if(ret=='\n') {
			ret = 0;
			if(prev!=',' || (b!='{' && b!='[')) {
				os.write('\n');
				for(int i=0;i<indent;++i) {
					os.write(TWO_SPACE);
				}
			}
		}
		switch(b) {
			case '{':
			case '[':	
					ret = '\n';
					++indent;
					break;
			case '}':
			case ']': 
					--indent;
					os.write('\n');
					for(int i=0;i<indent;++i) {
						os.write(TWO_SPACE);
					}
					break;
			case ',':
					ret = '\n';
					break;
					
		}
		os.write(b);
		prev = b;
	}
	public void resetIndent() {
		indent = 1;
	}

	@Override
	public void flush() throws IOException {
		os.flush();
	}

	@Override
	public void close() throws IOException {
		if(closeable) {
			os.close();
		}
	}

}
