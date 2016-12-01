/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;


public interface EnvStore<TRANS extends Trans> extends Env, Store, TransCreate<TRANS>{

}
