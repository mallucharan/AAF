/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.authz.service.test;

import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.att.aft.dme2.api.DME2Manager;
import com.att.authz.env.AuthzEnv;
import com.att.cadi.client.Future;
import com.att.cadi.config.Config;
import com.att.cadi.config.SecurityInfo;
import com.att.cadi.config.SecurityInfoC;
import com.att.cadi.dme2.DME2Locator;
import com.att.cadi.http.HBasicAuthSS;
import com.att.cadi.http.HMangr;

public class AbsServiceTest {
	private static final int TIMEOUT = 60000;
	protected static AuthzEnv env;
	protected static HMangr hman;
	protected static HBasicAuthSS basicAuthSS;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Properties props = System.getProperties();
		props.put("AFT_LATITUDE","32.780140");
		props.put("AFT_LONGITUDE","-96.800451");
		props.put("AFT_ENVIRONMENT","AFTUAT");
		props.put("DME2_EP_REGISTRY_CLASS","DME2FS");
		props.put("AFT_DME2_EP_REGISTRY_FS_DIR","../dme2reg");
		
		props.put(Config.CADI_KEYFILE, "../common/keyfile");
		
		env = new AuthzEnv(props);
		DME2Manager dm = new DME2Manager("DME2Manager TestHClient",props);
		DME2Locator loc = new DME2Locator(env,dm,"com.att.authz.AuthorizationService","2.0","DEV","BAU_SE");
		System.out.println(loc.get(loc.best()));
		hman = new HMangr(env,loc);
		
		SecurityInfoC<HttpURLConnection> si = new SecurityInfoC<HttpURLConnection>(env);
		basicAuthSS = new HBasicAuthSS(
				"???", 
				env.decrypt("enc:???",false), 
				si);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// Nothing to close... DRI Client doesn't need it
	}

	protected<T> T call(Future<T> fp, int ... acceptable) throws Exception {
		if(!fp.get(TIMEOUT)) {
			boolean fail = true;
			for(int a : acceptable) {
				if(fp.code()==a) fail=false;
			}
			String msg = fp.code() + fp.body();
			System.out.println(msg);
			if(fail) {
				assertTrue(msg,false);
			}
			return null;
		} else {
			return fp.value;
		}
	}

	protected void expect(Future<?> fp, int ... expected) throws Exception {
		fp.get(TIMEOUT);
		boolean received = false;
		StringBuilder msg = new StringBuilder("Expected ");
		
		for(int a : expected) {
			msg.append(a);
			msg.append(',');
			if(fp.code()==a) received=true;
		}
		msg.append(" but received ");
		msg.append(fp.code());
		assertTrue(msg.toString(),received);
	}
		


}
