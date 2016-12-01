/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.dao;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Bytification {
	public ByteBuffer bytify() throws IOException;
	public void reconstitute(ByteBuffer bb) throws IOException;
}
