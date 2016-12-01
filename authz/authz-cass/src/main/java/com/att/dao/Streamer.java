/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Streamer<DATA> {
	public abstract void marshal(DATA data, DataOutputStream os) throws IOException;
	public abstract void unmarshal(DATA data, DataInputStream is) throws IOException;
}
