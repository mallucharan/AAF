/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.log4j;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface PIDAccess extends Library {
	PIDAccess INSTANCE = (PIDAccess) Native.loadLibrary("c", PIDAccess.class);
    int getpid ();
}
