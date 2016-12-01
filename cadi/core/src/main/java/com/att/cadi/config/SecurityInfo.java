/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

import com.att.cadi.Access;
import com.att.cadi.SecuritySetter;


public class SecurityInfo<CLIENT> {
	
	private static final String HTTPS_PROTOCOLS = "https.protocols";
	private static final String REGEX_COMMA = "\\s,\\s";
	private static final String SslKeyManagerFactoryAlgorithm;
	
	private SSLSocketFactory scf;
	private X509KeyManager[] km;
	private TrustManager[] tm;
	private HostnameVerifier trustAll;
	public final String default_alias;
	public SecuritySetter<CLIENT> defSS;

	// Change Key Algorithms for IBM's VM.  Could put in others, if needed.
	static {
		if(System.getProperty("java.vm.vendor").equalsIgnoreCase("IBM Corporation")) {
			SslKeyManagerFactoryAlgorithm = "IbmX509";
		} else {
			SslKeyManagerFactoryAlgorithm = "SunX509";
		}
	}
	

	public SecurityInfo(final Access access) throws GeneralSecurityException, IOException {
		// reuse DME2 Properties for convenience if specific Properties don't exist
		String keyStore = access.getProperty(Config.CADI_KEYSTORE,
				access.getProperty(Config.AFT_DME2_KEYSTORE,null));
		String keyStorePasswd = access.getProperty(Config.CADI_KEYSTORE_PASSWORD,
				access.getProperty(Config.AFT_DME2_KEYSTORE_PASSWORD, null));
		keyStorePasswd = keyStorePasswd==null?null:access.decrypt(keyStorePasswd,false);
		String trustStore = access.getProperty(Config.CADI_TRUSTSTORE,
				access.getProperty(Config.AFT_DME2_TRUSTSTORE, null));
		String trustStorePasswd = access.getProperty(Config.CADI_TRUSTSTORE_PASSWORD,
				access.getProperty(Config.AFT_DME2_TRUSTSTORE_PASSWORD,null));
		trustStorePasswd = trustStorePasswd==null?null:access.decrypt(trustStorePasswd,false);
		default_alias = access.getProperty(Config.CADI_ALIAS, 
				access.getProperty(Config.AFT_DME2_CLIENT_SSL_CERT_ALIAS,null));
		String keyPasswd = access.getProperty(Config.CADI_KEY_PASSWORD,null);
		keyPasswd = keyPasswd==null?keyStorePasswd:access.decrypt(keyPasswd,false);
		final boolean trustAllX509 = "true".equalsIgnoreCase(access.getProperty(Config.CADI_TRUST_ALL_X509,
				access.getProperty(Config.AFT_DME2_SSL_TRUST_ALL,"false")));
		if(trustAllX509) {
			System.setProperty(Config.AFT_DME2_SSL_TRUST_ALL, "true");
		}
		String https_protocols = access.getProperty(Config.CADI_PROTOCOLS, 
				access.getProperty("AFT_DME2_SSL_INCLUDE_PROTOCOLS", 
					access.getProperty(HTTPS_PROTOCOLS,"TLSv1.1,TLSv1.2")
					));
		System.setProperty(HTTPS_PROTOCOLS,https_protocols);
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(SslKeyManagerFactoryAlgorithm);
		File file;

		ArrayList<X509KeyManager> kmal = new ArrayList<X509KeyManager>();

		if(keyStore==null || keyStorePasswd == null) { 
			km = new X509KeyManager[0];
		} else {
			for(String ksname : keyStore.split(REGEX_COMMA)) {
				file = new File(ksname);
				String keystoreFormat;
				if(ksname.endsWith("pkcs12")) {
					keystoreFormat = "PKCS12";
				} else {
					keystoreFormat = "JKS";
				}
				if(file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					try {
						KeyStore ks = KeyStore.getInstance(keystoreFormat);
						ks.load(fis, keyStorePasswd.toCharArray());
						kmf.init(ks, keyPasswd.toCharArray());
					} finally {
						fis.close();
					}
				}
			}
			for(KeyManager km : kmf.getKeyManagers()) {
				if(km instanceof X509KeyManager) {
					kmal.add((X509KeyManager)km);
				}
			}
			km = new X509KeyManager[kmal.size()];
			kmal.toArray(km);
		}

		if(trustAllX509 || trustStore==null || trustStorePasswd == null) { 
			tm = new TrustManager[] {
					new X509TrustManager() {
						private final String text = (trustAllX509?"X509 validation turned off":"Cannot validate X509 Client Validity: No TrustStore set");
			            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			                return null;
			            }
			            public void checkClientTrusted(
			                java.security.cert.X509Certificate[] certs, String authType) {
			            	access.log(Access.Level.WARN, text);
			            }
			            public void checkServerTrusted(
			                java.security.cert.X509Certificate[] certs, String authType) {
			            	access.log(Access.Level.WARN, text);
			            }
			        }
				};
		} else {
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(SslKeyManagerFactoryAlgorithm);
			for(String tsname : trustStore.split(REGEX_COMMA)) {
				file = new File(tsname);
				if(file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					try {
						KeyStore ts = KeyStore.getInstance("JKS");
						ts.load(fis, trustStorePasswd.toCharArray());
						tmf.init(ts); 
					} finally {
						fis.close();
					}
				}
			}
			tm = tmf.getTrustManagers();
		}
		
		// Create ability to turn off Service IP Checking... but Warn in Logs.
		trustAll = trustAllX509 || trustStore==null
				?new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session) {
						access.log(Access.Level.DEBUG,"Hostname Verification:",urlHostName,"vs.",session.getPeerHost());
			            return true;
					}
				}
				:null;

		SSLContext ctx = SSLContext.getInstance("SSL");
		ctx.init(km, tm, null);
		scf = ctx.getSocketFactory();
	}

	/**
	 * @return the scf
	 */
	public SSLSocketFactory getSSLSocketFactory() {
		return scf;
	}


	/**
	 * @return the km
	 */
	public X509KeyManager[] getKeyManagers() {
		return km;
	}


	/**
	 * @return the tm
	 */
	public TrustManager[] getTrustManagers() {
		return tm;
	}


	public void setSocketFactoryOn(HttpsURLConnection hsuc) {
		hsuc.setSSLSocketFactory(scf);
		if(trustAll!=null) {
			hsuc.setHostnameVerifier(trustAll);
		}
	}

	public SecurityInfo<CLIENT> set(SecuritySetter<CLIENT> defSS) {
		this.defSS = defSS;
		return this;
	}

}
