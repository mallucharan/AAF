/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env.log4j;

import java.util.logging.Logger;


public class LogTest
{
    public static void main(String[] args) throws Exception
    {
    	LogFileNamer lfn = new LogFileNamer("authz");
    	lfn.setAppender("service");
    	lfn.setAppender("init");
    	lfn.setAppender("audit");
    	lfn.setAppender("test");
    	lfn.configure("src/test/resources/log4j-test.properties");
        Logger log = Logger.getLogger( "init" );

        

        log.info("Hello");
    }
}
