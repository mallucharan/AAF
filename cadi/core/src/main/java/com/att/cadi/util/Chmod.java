/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.util;

import java.io.File;
import java.io.IOException;

public interface Chmod {
	public void chmod(File f) throws IOException;
	
	public static final Chmod to755 = new Chmod() {
		public void chmod(File f) throws IOException {
			f.setExecutable(true, false);
			f.setExecutable(true, true);
			f.setReadable(true, false);
			f.setReadable(true, true);
			f.setWritable(false, false);
			f.setWritable(true, true);
		}
	};

	public static final Chmod to644 = new Chmod() {
		public void chmod(File f) throws IOException {
			f.setExecutable(false, false);
			f.setExecutable(false, true);
			f.setReadable(true, false);
			f.setReadable(true, true);
			f.setWritable(false, false);
			f.setWritable(true, true);
		}
	};

	public static final Chmod to400 = new Chmod() {
		public void chmod(File f) throws IOException {
			f.setExecutable(false, false);
			f.setExecutable(false, true);
			f.setReadable(false, false);
			f.setReadable(true, true);
			f.setWritable(false, false);
			f.setWritable(false, true);
		}
	};
}