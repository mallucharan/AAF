/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/

package com.att.cadi.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import com.att.cadi.AbsUserCache;
import com.att.cadi.Access;
import com.att.cadi.Access.Level;
import com.att.cadi.CachingLur;
import com.att.cadi.CadiException;
import com.att.cadi.CredVal;
import com.att.cadi.Locator;
import com.att.cadi.Lur;
import com.att.cadi.Symm;
import com.att.cadi.TrustChecker;
import com.att.cadi.lur.EpiLur;
import com.att.cadi.lur.LocalLur;
import com.att.cadi.lur.NullLur;
import com.att.cadi.taf.HttpEpiTaf;
import com.att.cadi.taf.HttpTaf;
import com.att.cadi.taf.basic.BasicHttpTaf;
import com.att.cadi.taf.cert.X509Taf;
import com.att.cadi.taf.dos.DenialOfServiceTaf;

/**
 * Create a Consistent Configuration mechanism, even when configuration styles are as vastly different as
 * Properties vs JavaBeans vs FilterConfigs...
 * 
 *
 */
public class Config {

	public static final String UTF_8 = "UTF-8";

	// Property Names associated with configurations.
	// As of 1.0.2, these have had the dots removed so as to be compatible with JavaBean style
	// configurations as well as property list style.
	public static final String HOSTNAME = "hostname";
	public static final String CADI_PROP_FILES = "cadi_prop_files"; // Additional Properties files (separate with ;)
	public static final String CADI_LOGLEVEL = "cadi_loglevel";
	public static final String CADI_KEYFILE = "cadi_keyfile";
	public static final String CADI_KEYSTORE = "cadi_keystore";
	public static final String CADI_KEYSTORE_PASSWORD = "cadi_keystore_password";
	public static final String CADI_ALIAS = "cadi_alias";
	public static final String CADI_LOGINPAGE_URL = "cadi_loginpage_url";

	public static final String CADI_KEY_PASSWORD = "cadi_key_password";
	public static final String CADI_TRUSTSTORE = "cadi_truststore";
	public static final String CADI_TRUSTSTORE_PASSWORD = "cadi_truststore_password";
	public static final String CADI_X509_ISSUERS = "cadi_x509_issuers";
	public static final String CADI_TRUST_ALL_X509 = "cadi_trust_all_x509";
	public static final String CADI_PROTOCOLS = "cadi_protocols";
	public static final String CADI_NOAUTHN = "cadi_noauthn";
	public static final String CADI_LOC_LIST = "cadi_loc_list";
	
	public static final String CADI_TRUST_PROP = "cadi_trust_prop";
	public static final String CADI_USER_CHAIN = "USER_CHAIN";

	
	
	public static final String CSP_DOMAIN = "csp_domain";
	public static final String CSP_HOSTNAME = "csp_hostname";
	public static final String CSP_DEVL_LOCALHOST = "csp_devl_localhost";
	public static final String CSP_USER_HEADER = "CSP_USER";
	public static final String CSP_SYSTEMS_CONF = "CSPSystems.conf";
    public static final String CSP_SYSTEMS_CONF_FILE = "csp_systems_conf_file";


	public static final String TGUARD_ENV="tguard_env";
	public static final String TGUARD_DOMAIN = "tguard_domain";
	public static final String TGUARD_TIMEOUT = "tguard_timeout";
	public static final String TGUARD_TIMEOUT_DEF = "5000";
	public static final String TGUARD_CERTS = "tguard_certs"; // comma delimited SHA-256 finger prints
//	public static final String TGUARD_DEVL_LOCALHOST = "tguard_devl_localhost";
//	public static final String TGUARD_USER_HEADER = "TGUARD_USER";

	public static final String LOCALHOST_ALLOW = "localhost_allow";
	public static final String LOCALHOST_DENY = "localhost_deny";
	
	public static final String BASIC_REALM = "basic_realm";  // what is sent to the client 
	public static final String BASIC_WARN = "basic_warn";  // Warning of insecure channel 
	public static final String USERS = "local_users";
	public static final String GROUPS = "local_groups";
	public static final String WRITE_TO = "local_writeto"; // dump RBAC to local file in Tomcat Style (some apps use)
	
	public static final String AAF_ENV = "aaf_env";
	public static final String AAF_ROOT_NS = "aaf_root_ns";
	public static final String AAF_ROOT_COMPANY = "aaf_root_company";
	public static final String AAF_URL = "aaf_url"; //URL for AAF... Use to trigger AAF configuration
	public static final String AAF_MECHID = "aaf_id";
	public static final String AAF_MECHPASS = "aaf_password";
	public static final String AAF_LUR_CLASS = "aaf_lur_class";
	public static final String AAF_TAF_CLASS = "aaf_taf_class";
	public static final String AAF_CONNECTOR_CLASS = "aaf_connector_class";
	public static final String AAF_LOCATOR_CLASS = "aaf_locator_class";
	public static final String AAF_CONN_TIMEOUT = "aaf_conn_timeout";
	public static final String AAF_CONN_TIMEOUT_DEF = "3000";
	public static final String AAF_READ_TIMEOUT = "aaf_timeout";
	public static final String AAF_READ_TIMEOUT_DEF = "5000";
	public static final String AAF_USER_EXPIRES = "aaf_user_expires";
	public static final String AAF_USER_EXPIRES_DEF = "600000"; // Default is 10 mins
	public static final String AAF_CLEAN_INTERVAL = "aaf_clean_interval";
	public static final String AAF_CLEAN_INTERVAL_DEF = "30000"; // Default is 30 seconds
	public static final String AAF_REFRESH_TRIGGER_COUNT = "aaf_refresh_trigger_count";
	public static final String AAF_REFRESH_TRIGGER_COUNT_DEF = "3"; // Default is 10 mins
	
	public static final String AAF_HIGH_COUNT = "aaf_high_count";
	public static final String AAF_HIGH_COUNT_DEF = "1000"; // Default is 1000 entries
	public static final String AAF_PERM_MAP = "aaf_perm_map";
	public static final String AAF_DEPLOYED_VERSION = "DEPLOYED_VERSION";
	public static final String AAF_CERT_IDS = "aaf_cert_ids";
	public static final String AAF_DEBUG_IDS = "aaf_debug_ids"; // comma delimited
	
	public static final String CM_URL = "cm_url";
	public static final String CM_TRUSTED_CAS = "cm_trusted_cas";

	public static final String PATHFILTER_URLPATTERN = "pathfilter_urlpattern";
	public static final String PATHFILTER_STACK = "pathfilter_stack";
	public static final String PATHFILTER_NS = "pathfilter_ns";
	public static final String PATHFILTER_NOT_AUTHORIZED_MSG = "pathfilter_not_authorized_msg";

	public static final String AFT_LATITUDE="AFT_LATITUDE";
	public static final String AFT_LONGITUDE="AFT_LONGITUDE";
	public static final String AFT_ENVIRONMENT="AFT_ENVIRONMENT";
	public static final String AFT_DME2_TRUSTSTORE_PASSWORD = "AFT_DME2_TRUSTSTORE_PASSWORD";
	public static final String AFT_DME2_TRUSTSTORE = "AFT_DME2_TRUSTSTORE";
	public static final String AFT_DME2_KEYSTORE_PASSWORD = "AFT_DME2_KEYSTORE_PASSWORD";
	public static final String AFT_DME2_KEY_PASSWORD = "AFT_DME2_KEY_PASSWORD";
	public static final String AFT_DME2_KEYSTORE = "AFT_DME2_KEYSTORE";
	public static final String AFT_DME2_CLIENT_KEYSTORE = "AFT_DME2_CLIENT_KEYSTORE";
	public static final String AFT_DME2_CLIENT_KEYSTORE_PASSWORD = "AFT_DME2_CLIENT_KEYSTORE_PASSWORD";
	public static final String AFT_DME2_SSL_TRUST_ALL = "AFT_DME2_SSL_TRUST_ALL";
	public static final String AFT_DME2_CLIENT_SSL_CERT_ALIAS = "AFT_DME2_CLIENT_SSL_CERT_ALIAS"; 

	
	// This one should go unpublic
	public static final String AAF_DEFAULT_REALM = "aaf_default_realm";
	private static String defaultRealm="none";

	public static final String AAF_DOMAIN_SUPPORT = "aaf_domain_support";
	public static final String AAF_DOMAIN_SUPPORT_DEF = ".com";


	public static void configPropFiles(Get getter, Access access) throws CadiException {
		String pf;
		if((pf = getter.get(CADI_PROP_FILES, null,true))!=null) {
			for(String str : pf.split(";")) {
				InputStream is = access.classLoader().getResourceAsStream(str);
				if(is==null) {
					File file = new File(str);
					if(file.exists()) {
						try {
//							access.log(Level.INIT, "Cadi Property File is of length",file.length());
							access.log(Level.INIT, "Loading Cadi Property File: \""+file.getCanonicalPath()+'"');
							FileInputStream fis = new FileInputStream(file);						
							try {
								access.load(fis);
							} finally {
								fis.close();
							}
						} catch (IOException e) {
							access.log(e);
							throw new CadiException(e);
						}
					} else {
						try {
							// Put in System Err too, because Logging may not be enabled quite yet
							String msg = "Cadi Property File: \"" + file.getCanonicalPath() + "\" does not exist!";
							access.log(Level.ERROR, msg);
						} catch (IOException e) {
							access.log(e);
							throw new CadiException(e);
						}
					}
				} else {
					access.log(Level.INIT, "Cadi Property File \"",str,"\" loading from Classpath");
					try {
						try {
							access.load(is);
						} finally {
							is.close();
						}
					} catch (IOException e) {
						throw new CadiException(e);
					}
				}
			}
		}
			
		try {
			boolean hasCSP = getter.get(Config.CSP_DOMAIN, null,true)!=null;
			defaultRealm = getter.get(Config.AAF_DEFAULT_REALM,
					hasCSP?"csp.att.com":
					getter.get(Config.BASIC_REALM,
						getter.get(HOSTNAME, 
								InetAddress.getLocalHost().getHostName(),
								true),
						true),
					true);
		} catch (UnknownHostException e) {
			defaultRealm="none";
		}
	}
	

	public static HttpTaf configHttpTaf(Access access, TrustChecker tc, Get getter, CredVal up, Lur lur, Object ... additionalTafLurs) throws CadiException {
		/////////////////////////////////////////////////////
		// Setup AAFCon for any following
		/////////////////////////////////////////////////////
		Object aafcon = null;
		if(lur != null) {
			Field f = null;
			try {
				f = lur.getClass().getField("aaf");
				aafcon = f.get(lur);
			} catch (Exception nsfe) {
			}
		}
		
		// IMPORTANT!  Don't attempt to load AAF Connector if there is no AAF URL
		String aafURL = getter.get(AAF_URL,null,false);
		if(aafURL!=null && aafcon==null) {
			aafcon = loadAAFConnector(access, getter,aafURL);	
		}
		
		HttpTaf taf;
		// Setup Host, in case Network reports an unusable Hostname (i.e. VTiers, VPNs, etc)
		String hostname = getter.get(HOSTNAME,null, true);
		if(hostname==null)
			try {
				hostname = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e1) {
				throw new CadiException("Unable to determine Hostname",e1);
			}
		
		access.log(Level.INIT, "Hostname set to",hostname);
		// Get appropriate TAFs
		ArrayList<HttpTaf> htlist = new ArrayList<HttpTaf>();

		/////////////////////////////////////////////////////
		// Add a Denial of Service TAF
		// Note: how IPs and IDs are added are up to service type.
		// They call "DenialOfServiceTaf.denyIP(String) or denyID(String)
		/////////////////////////////////////////////////////
		htlist.add(new DenialOfServiceTaf(access));

		/////////////////////////////////////////////////////
		// Configure LocalHost 
		/////////////////////////////////////////////////////
//		//    allow, meaning treat as Validated (This is admittedly weak.  It just means someone has access to box)
//		boolean localhost_allow = "TRUE".equalsIgnoreCase(getter.get(LOCALHOST_ALLOW,"FALSE",true));
//		//    deny, meaning we can deny any access from local host (127.0.0.1, ::1, etc)
//		boolean localhost_deny = "TRUE".equalsIgnoreCase(getter.get(LOCALHOST_DENY,"FALSE",true));
//		// if at least one is true, install LocalHostTAF.  If both are false, it's not worth the CPU, skip it
//		// Localhost (local box access)
//		if(localhost_allow || localhost_deny) { 
//			// log("Localhost Authorization is "); // already logged
//			htlist.add(new LocalhostTaf(access,localhost_allow,localhost_deny));
//			if(localhost_allow)  {
//				access.log(Level.INIT,"WARNING! Localhost Authentication is enabled.  This is ONLY to be used in a Development setting, never Test or Production");
//			}
//			if(localhost_deny)  {
//				access.log(Level.INIT,"Localhost Access to this service is disabled.");
//			}
//
//		} else {
//			access.log(Level.INIT,"Localhost Authorization is disabled");
//		}
				
		/////////////////////////////////////////////////////
		// Configure Basic Auth (local content)
		/////////////////////////////////////////////////////
		String basic_realm = getter.get(BASIC_REALM,null,true);
		boolean basic_warn = "TRUE".equals(getter.get(BASIC_WARN,"FALSE",false));
		if(basic_realm!=null && up!=null) {
			access.log(Level.INIT,"Basic Authorization is enabled using realm",basic_realm);
			// Allow warning about insecure channel to be turned off
			if(!basic_warn)access.log(Level.INIT,"WARNING! The basic_warn property has been set to false.",
					" There will be no additional warning if Basic Auth is used on an insecure channel"
					);
			String aafCleanup = getter.get(AAF_USER_EXPIRES,AAF_USER_EXPIRES_DEF,true); // Default is 10 mins
			long userExp = Long.parseLong(aafCleanup);

			htlist.add(new BasicHttpTaf(access, up, basic_realm, userExp, basic_warn));
		} else {
			access.log(Level.INIT,"Local Basic Authorization is disabled.  Enable by setting basic_realm=<appropriate realm, i.e. my.att.com>");
		}
		
		/////////////////////////////////////////////////////
		// Configure AAF Driven Basic Auth
		/////////////////////////////////////////////////////
		HttpTaf aaftaf=null;
		if(aafcon==null) {
			access.log(Level.INIT,"AAF Connection (AAFcon) is null.  Cannot create an AAF TAF");
		} else if(aafURL==null) {
			access.log(Level.INIT,"No AAF URL in properties, Cannot create an AAF TAF");
		} else {// There's an AAF_URL... try to configure an AAF 
			String defName = aafURL.contains("version=2.0")?"com.att.cadi.aaf.v2_0.AAFTaf":"";
			String aafTafClassName = getter.get(AAF_TAF_CLASS,defName,true);
			// Only 2.0 available at this time
			if("com.att.cadi.aaf.v2_0.AAFTaf".equals(aafTafClassName)) { 
				try {
					Class<?> aafTafClass = loadClass(access,aafTafClassName);
					Class<?> aafConClass = loadClass(access,"com.att.cadi.aaf.v2_0.AAFCon");

					Constructor<?> cstr = aafTafClass.getConstructor(aafConClass,boolean.class);
					if(cstr!=null) {
						aaftaf = (HttpTaf)cstr.newInstance(aafcon,basic_warn);
						if(aaftaf==null) {
							access.log(Level.INIT,"ERROR! AAF TAF Failed construction.  NOT Configured");
						} else {
							access.log(Level.INIT,"AAF TAF Configured to ",aafURL);
							// Note: will add later, after all others configured
						}
					}
				} catch(Exception e) {
					access.log(Level.INIT,"ERROR! AAF TAF Failed construction.  NOT Configured");
				}
			}
		}
		
		
		String alias = getter.get(CADI_ALIAS,null, true);

		/////////////////////////////////////////////////////
		// Configure tGuard... (AT&T Client Repo)
		/////////////////////////////////////////////////////
		// TGUARD Environment, translated to any other remote Environment validation mechanism...
		String tGuard_domain = getter.get(TGUARD_DOMAIN,null,true);
		String tGuard_env = getter.get(TGUARD_ENV, null,true);

		if(!("PROD".equals(tGuard_env) || "STAGE".equals(tGuard_env))) {
			access.log(Level.INIT, "tGuard Authorization is disabled.  Enable by setting", TGUARD_ENV, "to \"PROD\" or \"STAGE\"");
		} else if(tGuard_domain==null) {
			access.log(Level.INIT,TGUARD_DOMAIN + " must be set:  tGuard Authorization is disabled.");
		} else if(alias == null) {
			access.log(Level.INIT,CADI_ALIAS + " must be set:  tGuard Authorization is disabled.");
		} else {
			try {
				Class<?> tGuardClass = loadClass(access,"com.att.cadi.tguard.TGuardHttpTaf");
				if(aaftaf!=null) {
					Constructor<?> tGuardCnst = tGuardClass.getConstructor(new Class[]{Access.class, AbsUserCache.class});
					htlist.add((HttpTaf)tGuardCnst.newInstance(new Object[] {access,aaftaf}));
					access.log(Level.INIT,"tGuard Authorization is enabled on",tGuard_env,"on the",tGuard_domain," tGuard Domain");
				} else {
					Constructor<?> tGuardCnst = tGuardClass.getConstructor(new Class[]{Access.class, int.class, int.class, int.class});
					htlist.add((HttpTaf)tGuardCnst.newInstance(new Object[] {
							access,
							Integer.parseInt(getter.get(AAF_CLEAN_INTERVAL,AAF_CLEAN_INTERVAL_DEF,true)),
							Integer.parseInt(getter.get(AAF_HIGH_COUNT, AAF_HIGH_COUNT_DEF,true)),
							Integer.parseInt(getter.get(AAF_REFRESH_TRIGGER_COUNT, AAF_REFRESH_TRIGGER_COUNT_DEF,true))
							}));
					access.log(Level.INIT,"tGuard Authorization is enabled on",tGuard_env,"on the",tGuard_domain," tGuard Domain");
				}
			} catch(Exception e) {
				access.log(e, Level.INIT,"tGuard Class cannot be loaded:  tGuard Authorization is disabled.");
			}
		}
		
		/////////////////////////////////////////////////////
		// Adding BasicAuth (AAF) last, after other primary Cookie Based
		// Needs to be before Cert... see below
		/////////////////////////////////////////////////////
		if(aaftaf!=null)
			htlist.add(aaftaf);


		/////////////////////////////////////////////////////
		// Configure Client Cert TAF
		// Note: Needs to be after Basic Auth, because otherwise, 
		//       a Mutual SSL connection might give wrong Authentication
		/////////////////////////////////////////////////////
		String truststore = getter.get(CADI_TRUSTSTORE, getter.get("AFT_DME2_TRUSTSTORE", null, false), true);
		if(truststore!=null) {
			String truststore_pwd = getter.get(CADI_TRUSTSTORE_PASSWORD, getter.get("AFT_DME2_TRUSTSTORE_PASSWORD",null,false), true);
			if(truststore_pwd!=null) {
				if(truststore_pwd.startsWith(Symm.ENC)) {
					try {
						truststore_pwd = access.decrypt(truststore_pwd,false);
					} catch (IOException e) {
						throw new CadiException(CADI_TRUSTSTORE_PASSWORD + " cannot be decrypted",e);
					}
				}
				try {
//					if(aafcon!=null) {
//						Class<CertIdentity> cls = loadClass(access,"com.att.cadi.aaf.cert.AAFListedCertIdentity");
//						Constructor<CertIdentity> cnst = cls.getConstructor(Access.class, aafConClass);
//						
//						htlist.add(new X509Taf(access,lur,cnst.newInstance(access,aafcon)));
//						access.log(Level.INIT,"Certificate Authorization enabled with AAF Cert Identity");
//					} else {
						htlist.add(new X509Taf(access,lur));
						access.log(Level.INIT,"Certificate Authorization enabled");
//					}
//				} catch (ClassNotFoundException e) {
//					access.log(Level.INIT,"Certificate Authorization requires AAF Jars. It is now disabled",e);
//				} catch (NoSuchMethodException e) {
//					access.log(Level.INIT,"Certificate Authorization requires AAFListedCertIdentity. It is now disabled",e);
				} catch (SecurityException e) {
					access.log(Level.INIT,"AAFListedCertIdentity cannot be instantiated. Certificate Authorization is now disabled",e);
//				} catch (InstantiationException e) {
//					access.log(Level.INIT,"AAFListedCertIdentity cannot be instantiated. Certificate Authorization is now disabled",e);
				} catch (IllegalArgumentException e) {
					access.log(Level.INIT,"AAFListedCertIdentity cannot be instantiated. Certificate Authorization is now disabled",e);
//				} catch (InvocationTargetException e) {
//					access.log(Level.INIT,"AAFListedCertIdentity cannot be instantiated. Certificate Authorization is now disabled",e);
//				} catch (IllegalAccessException e) {
//					access.log(Level.INIT,"Certificate Authorization requires AAFListedCertIdentity. It is now disabled",e);
				} catch (CertificateException e) {
					access.log(Level.INIT,"Certificate Authorization failed, it is disabled",e);
				} catch (NoSuchAlgorithmException e) {
					access.log(Level.INIT,"Certificate Authorization failed, wrong Security Algorithm",e);
				}
			}
		} else {
			access.log(Level.INIT,"Certificate Authorization not enabled");

		}


		/////////////////////////////////////////////////////
		// Any Additional Lurs passed in Constructor
		/////////////////////////////////////////////////////
		for(Object additional : additionalTafLurs) {
			if(additional instanceof HttpTaf) {
				htlist.add((HttpTaf)additional);
				access.log(Level.INIT,additional);
			}
		}

		/////////////////////////////////////////////////////
		// Create EpiTaf from configured TAFs
		/////////////////////////////////////////////////////
		if(htlist.size()==1) {
			// just return the one
			taf = htlist.get(0);
		} else {
			HttpTaf[] htarray = new HttpTaf[htlist.size()];
			htlist.toArray(htarray);
			Locator locator = loadLocator(access, getter.get(CADI_LOGINPAGE_URL, null, true));
			
			taf = new HttpEpiTaf(access,locator, tc, htarray); // ok to pass locator == null
			String level = getter.get(CADI_LOGLEVEL, null, true);
			if(level!=null) {
				access.setLogLevel(Level.valueOf(level));
			}
		}
		
		return taf;
	}
	
	public static Lur configLur(Get getter, Access access, Object ... additionalTafLurs) throws CadiException {
		List<Lur> lurs = new ArrayList<Lur>();
		
		/////////////////////////////////////////////////////
		// Configure a Local Property Based RBAC/LUR
		/////////////////////////////////////////////////////
		try {
			String users = getter.get(USERS,null,false);
			String groups = getter.get(GROUPS,null,false);

			if(groups!=null || users!=null) {
				LocalLur ll;
				lurs.add(ll = new LocalLur(access, users, groups)); // note b64==null is ok.. just means no encryption.
				
				String writeto = getter.get(WRITE_TO,null,false);
				if(writeto!=null) {
					String msg = UsersDump.updateUsers(writeto, ll);
					if(msg!=null) access.log(Level.INIT,"ERROR! Error Updating ",writeto,"with roles and users:",msg);
				}
			}
		} catch (IOException e) {
			throw new CadiException(e);
		}
		
		/////////////////////////////////////////////////////
		// Configure the AAF Lur (if any)
		/////////////////////////////////////////////////////
		String aafURL = getter.get(AAF_URL,null,false); // Trigger Property
			
		if(aafURL==null) {
			access.log(Level.INIT,"No AAF LUR properties, AAF will not be loaded");
		} else {// There's an AAF_URL... try to configure an AAF
			String aafLurClassStr = getter.get(AAF_LUR_CLASS,"com.att.cadi.aaf.v2_0.AAFLurPerm",true);
			////////////AAF Lur 2.0 /////////////
			if(aafLurClassStr.startsWith("com.att.cadi.aaf.v2_0")) { 
				try {
					Object aafcon = loadAAFConnector(access, getter, aafURL);
					if(aafcon==null) {
						access.log(Level.INIT,"AAF LUR class,",aafLurClassStr,"cannot be constructed without valid AAFCon object.");
					} else {
						Class<?> aafAbsAAFCon = loadClass(access, "com.att.cadi.aaf.v2_0.AAFCon");
						Method mNewLur = aafAbsAAFCon.getMethod("newLur");
						Object aaflur = mNewLur.invoke(aafcon);
	
						if(aaflur==null) {
							access.log(Level.INIT,"ERROR! AAF LUR Failed construction.  NOT Configured");
						} else {
							access.log(Level.INIT,"AAF LUR Configured to ",aafURL);
							lurs.add((Lur)aaflur);
							String debugIDs = getter.get(Config.AAF_DEBUG_IDS, null, true);
							if(debugIDs !=null && aaflur instanceof CachingLur) {
								((CachingLur<?>)aaflur).setDebug(debugIDs);
							}
						}
					}
				} catch (Exception e) {
					access.log(e,"AAF LUR class,",aafLurClassStr,"could not be constructed with given Constructors.");
				}
			} 
		} 

		/////////////////////////////////////////////////////
		// Any Additional passed in Constructor
		/////////////////////////////////////////////////////
		for(Object additional : additionalTafLurs) {
			if(additional instanceof Lur) {
				lurs.add((Lur)additional);
				access.log(Level.INIT, additional);
			}
		}

		/////////////////////////////////////////////////////
		// Return a Lur based on how many there are... 
		/////////////////////////////////////////////////////
		switch(lurs.size()) {
			case 0: 
				access.log(Level.INIT,"WARNING! No CADI LURs configured");
				// Return a NULL Lur that does nothing.
				return new NullLur();
			case 1:
				return lurs.get(0); // Only one, just return it, save processing
			default:
				// Multiple Lurs, use EpiLUR to handle
				Lur[] la = new Lur[lurs.size()];
				lurs.toArray(la);
				return new EpiLur(la);
		}
	}
	
	public static Object loadAAFConnector(Access access, Get getter, String aafURL) {
		Object aafcon = null;
		Class<?> aafConClass = null;

		try {
			if(aafURL!=null) {

					aafConClass = loadClass(access, "com.att.cadi.aaf.v2_0.AAFConDME2");
					aafcon = aafConClass.getConstructor(Access.class).newInstance(access);
				 

				if(aafcon!=null) {
					
					System.out.println("In if aafcon!=null ;;;;;;;;;;;;;;;;;;" +aafcon);
					String mechid = getter.get(Config.AAF_MECHID, null, true);
					String pass = getter.get(Config.AAF_MECHPASS, null, false);
					if(mechid!=null && pass!=null) {
						try {
							Method basicAuth = aafConClass.getMethod("basicAuth", String.class, String.class);
							basicAuth.invoke(aafcon, mechid,pass);
						} catch (NoSuchMethodException nsme) {
							// it's ok, don't use
						}
					}
				}
			}
		} catch (Exception e) {
			access.log(e,"AAF Connector could not be constructed with given Constructors.");
		}
		
		System.out.println("print the method return aafcon ;;;;;;;;;;;;;;;;;;" +aafcon);
		return aafcon;
	}

	public static Class<?> loadClass(Access access, String className) {
		Class<?> cls=null;
		try {
			cls = access.classLoader().loadClass(className);
		} catch (ClassNotFoundException cnfe) {
			try {
				cls = access.getClass().getClassLoader().loadClass(className);
			} catch (ClassNotFoundException cnfe2) {
				// just return null
			}
		}
		return cls;
	}

	public static Locator loadLocator(Access access, String url) {
		Locator locator = null;
		System.out.println("print the url in loadLocator --------------------------------" +url);
		if(url==null) {
			access.log(Level.INIT,"No URL for AAF Login Page. Disabled");
		} else {
			if(url.contains("DME2RESOLVE")) {
				try {
					Class<?> lcls = loadClass(access,"com.att.cadi.dme2.DME2Locator");
					Class<?> dmcls = loadClass(access,"com.att.aft.dme2.api.DME2Manager");
					Constructor<?> cnst = lcls.getConstructor(new Class[] {Access.class,dmcls,String.class});
					locator = (Locator)cnst.newInstance(new Object[] {access,null,url});
					access.log(Level.INFO, "DME2Locator enabled with " + url);
				} catch (Exception e) {
					access.log(Level.INIT,"AAF Login Page accessed by " + url + " requires DME2. It is now disabled",e);
				}
			} else {
				try {
					Class<?> cls = loadClass(access,"com.att.cadi.client.PropertyLocator");
					Constructor<?> cnst = cls.getConstructor(new Class[] {String.class});
					locator = (Locator)cnst.newInstance(new Object[] {url});
					access.log(Level.INFO, "PropertyLocator enabled with " + url);
				} catch (Exception e) {
					access.log(Level.INIT,"AAF Login Page accessed by " + url + " requires PropertyLocator. It is now disabled",e);
				}
			}
		}
		System.out.println("print locator in loadlocator method------------------------" +locator);
		return locator;
	}

	/*
	 * DME2 can only read Passwords as clear text properties.  Leaving in "System Properties" un-encrypted exposes these passwords
	 */
	private static class PasswordRemoval extends TimerTask {
		private Access access;
		private final List<String> pws;

		public PasswordRemoval(Access access) {
			this.access = access;
			pws = new ArrayList<String>();
		}
		
		@Override
		public void run() {
			for(String key:pws) {
				access.log(Level.INIT, "Scrubbing " + key);
				System.clearProperty(key);
			}
		}		
		public void add(String key) {
			pws.add(key);
		}
	}
	public static TimerTask cadiToDME2(Access access, Properties props) {
		// Look for and convert cadi props to DME2 Properties
		PasswordRemoval pr = new PasswordRemoval(access);
		for(String[] str : CONVERTER_STRINGS) {
			// Does it exist already?? 
			String value = props.getProperty(str[0],null);
			try {
				if(value==null) { // not, then pull from Access
					value = access.getProperty(str[0], null);
					if(value==null && str[1]!=null) {
						value = access.getProperty(str[1], null);
					}
					if(value!=null) {
						if(value.startsWith("enc:???")) {
							// Adding Passwords to this Properties will expose in GRM... !!! "hide" in System properties
							// props.setProperty(str[0], access.decrypt(value,false));
							props.remove(str[0]);
							System.setProperty(str[0], access.decrypt(value, false));
							pr.add(str[0]);
						} else {
							props.setProperty(str[0], value);
						}
					}
				// DME2 needs unencypted passwords in Properties
				} else {
					if(value.startsWith("enc:???")) {
						// Adding Passwords to this Properties will expose in GRM... !!! "hide" in System properties
						// props.setProperty(str[0], access.decrypt(value,false));
						props.remove(str[0]);
						System.setProperty(str[0], access.decrypt(value, false));
						props.remove(str[0]);
					}
				}
				if(value!=null && Y.equals(str[2])) {
					if(System.getProperty(str[0])==null ) {
						System.setProperty(str[0], value);
					}
				}
			} catch (IOException e) {
				access.log(Level.ERROR, "Problem decrypting property",str[0]);
			}
		}
		return pr;
	}

	private static final String Y = "Y";

	private static String[][] CONVERTER_STRINGS=new String[][] {
			{AFT_DME2_KEYSTORE,CADI_KEYSTORE,null},
			{AFT_DME2_KEYSTORE_PASSWORD,CADI_KEYSTORE_PASSWORD,null},
			{AFT_DME2_CLIENT_KEYSTORE,CADI_KEYSTORE,null},
			{AFT_DME2_CLIENT_KEYSTORE_PASSWORD,CADI_KEYSTORE_PASSWORD,null},
			{AFT_DME2_KEY_PASSWORD,CADI_KEY_PASSWORD,null},
			{AFT_DME2_KEY_PASSWORD,CADI_KEYSTORE_PASSWORD,null},
			{AFT_DME2_TRUSTSTORE,CADI_TRUSTSTORE,null},
			{AFT_DME2_TRUSTSTORE_PASSWORD,CADI_TRUSTSTORE_PASSWORD,null},
			{AFT_DME2_CLIENT_SSL_CERT_ALIAS,CADI_ALIAS,null},
			{AFT_DME2_SSL_TRUST_ALL,CADI_TRUST_ALL_X509,null},
			{AFT_DME2_CLIENT_SSL_CERT_ALIAS,CADI_ALIAS,null},
			{"https.protocols",CADI_PROTOCOLS,null},
			{"AFT_DME2_HOSTNAME",HOSTNAME,null},
			{"AFT_LATITUDE",null,Y},
			{"AFT_LONGITUDE",null,Y},
			{"AFT_ENVIRONMENT",null,Y},
			{"SCLD_PLATFORM",null,Y},
			{"DME2_EP_REGISTRY_CLASS",null,Y},// for Developer local access
			{"AFT_DME2_EP_REGISTRY_FS_DIR",null,Y},
			{"DME2.DEBUG",null,null},
			{"AFT_DME2_HTTP_EXCHANGE_TRACE_ON",null,null},
			{"AFT_DME2_SSL_ENABLE",null,null},
			{"AFT_DME2_SSL_WANT_CLIENT_AUTH",null,null},
			{"AFT_DME2_SSL_INCLUDE_PROTOCOLS",null,null},
			{"AFT_DME2_SSL_VALIDATE_CERTS",null,null},
			{"AFT_DME2_CLIENT_IGNORE_SSL_CONFIG",null,null},
			};


	// Set by CSP, or is hostname.
	public static String getDefaultRealm() {
		return defaultRealm;
	}

}
