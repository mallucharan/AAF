/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.client.test;

import java.net.HttpURLConnection;
import java.util.Properties;

import com.att.aft.dme2.api.DME2Manager;
import com.att.cadi.Access;
import com.att.cadi.CadiException;
import com.att.cadi.Locator;
import com.att.cadi.Locator.Item;
import com.att.cadi.SecuritySetter;
import com.att.cadi.client.Future;
import com.att.cadi.client.Rcli;
import com.att.cadi.client.Retryable;
import com.att.cadi.config.SecurityInfo;
import com.att.cadi.dme2.DME2Locator;
import com.att.cadi.http.HBasicAuthSS;
import com.att.cadi.http.HMangr;
import com.att.inno.env.APIException;

public class TestHClient {
	public static void main(String[] args) {
		try {
			Properties props = System.getProperties();
			props.put("AFT_LATITUDE","32.780140");
			props.put("AFT_LONGITUDE","-96.800451");
			props.put("AFT_ENVIRONMENT","AFTUAT");
//			props.put("DME2_EP_REGISTRY_CLASS","DME2FS");
//			props.put("AFT_DME2_EP_REGISTRY_FS_DIR","/Volumes/Data/src/authz/dme2reg");

			props.put("cadi_keystore","/Volumes/Data/src/authz/common/aaf.att.jks");
			props.put("cadi_keystore_password","enc:???");
			props.put("cadi_truststore","/Volumes/Data/src/authz/common/truststore.jks");
			props.put("cadi_truststore_password","enc:???");
			props.put("cadi_keyfile", "/Volumes/Data/src/authz/common/com.att.aaf.keyfile");
			// Local Testing on dynamic IP PC ***ONLY***
//			props.put("cadi_trust_all_x509", "true");
			
			Access access = new TestAccess();
			DME2Manager dm = new DME2Manager("DME2Manager TestHClient",props);
			Locator loc = new DME2Locator(access,dm,"com.att.authz.AuthorizationService","2.0","DEV","BAU_SE");

			for(Item item = loc.first(); item!=null; item=loc.next(item)) {
				System.out.println(loc.get(item));
			}
			
			
			SecurityInfo<HttpURLConnection> si = new SecurityInfo<HttpURLConnection>(access);
			SecuritySetter<HttpURLConnection> ss = new HBasicAuthSS("XX@NS", 
					access.decrypt("enc:???",false), si);
//			SecuritySetter<HttpURLConnection> ss = new X509SS(si, "aaf");
			
			HMangr hman = new HMangr(access,loc);
			try {
				hman.best(ss, new Retryable<Void>() {
					@Override
					public Void code(Rcli<?> cli) throws APIException, CadiException {
						Future<String> ft = cli.read("/authz/nss/com.att.aaf","text/json");  
						if(ft.get(10000)) {
							System.out.println("Hurray,\n"+ft.body());
						} else {
							System.out.println("not quite: " + ft.code());
						}
						return null;
					}});
			} finally {
				hman.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
