/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.client.test;

import java.net.URI;
import java.util.Properties;

import com.att.aft.dme2.api.DME2Manager;
import com.att.cadi.Access;
import com.att.cadi.client.Future;
import com.att.cadi.dme2.DME2ClientSS;
import com.att.cadi.dme2.DRcli;

public class TestDME2RcliClient {
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
			props.put("cadi_keyfile", "/Volumes/Data/src/authz/common/keyfile");
			
			// Local Testing on dynamic IP PC ***ONLY***
//			props.put("cadi_trust_all_x509", "true");		
			
			
			
			URI uri = new URI("https://DME2RESOLVE/service=com.att.authz.AuthorizationService/version=2.0/envContext=DEV/routeOffer=BAU_SE");
				 
			Access access = new TestAccess();
			DME2Manager dm = new DME2Manager("DME2Manager TestHClient",props);
			DRcli client = new DRcli(
					uri, 
					new DME2ClientSS(access,"XX@NS","enc:???"));
			
			client.setManager(dm)
				  .apiVersion("2.0")
				  .readTimeout(3000);
			
			Future<String> ft = client.read("/authz/nss/com.att.aaf","text/json");  
			if(ft.get(10000)) {
				System.out.println("Hurray,\n"+ft.body());
			} else {
				System.out.println("not quite: " + ft.code());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
