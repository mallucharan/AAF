/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi.dme2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509KeyManager;

import com.att.aft.dme2.api.DME2Client;
import com.att.cadi.CadiException;
import com.att.cadi.SecuritySetter;
import com.att.cadi.Symm;
import com.att.cadi.config.Config;
import com.att.cadi.config.SecurityInfo;
import com.att.inno.env.APIException;


public class DME2x509SS implements SecuritySetter<DME2Client> {
	private static final byte[] X509 = "x509 ".getBytes();
	private PrivateKey priv;
	private byte[] pub;
	private String cert;
	//private SecurityInfo<DME2Client> securityInfo;
	private String algo;
	private String alias;

	public DME2x509SS(SecurityInfo<DME2Client> si) throws APIException, IOException, CertificateEncodingException {
		this(null,si,false);
	}
	
	public DME2x509SS(SecurityInfo<DME2Client> si, boolean asDefault) throws APIException, IOException, CertificateEncodingException {
		this(null,si,asDefault);
	}
	
	public DME2x509SS(final String sendAlias, SecurityInfo<DME2Client> si) throws APIException, IOException, CertificateEncodingException {
		this(sendAlias, si, false);
	}

	public DME2x509SS(final String sendAlias, SecurityInfo<DME2Client> si, boolean asDefault) throws APIException, IOException, CertificateEncodingException {
		//securityInfo = si;
		if((alias=sendAlias) == null) {
			if(si.default_alias == null) {
				throw new APIException("JKS Alias is required to use X509SS Security.  Use " + Config.CADI_ALIAS +" to set default alias");
			} else {
				alias = si.default_alias;
			}
		}
		
		priv=null;
		X509KeyManager[] xkms = si.getKeyManagers();
		if(xkms==null) {
			System.out.println("need KeyManager");
			System.exit(1);
		}
		for(int i=0;priv==null&&i<xkms.length;++i) {
			priv = xkms[i].getPrivateKey(alias);
		}
		for(int i=0;cert==null&&i<xkms.length;++i) {
			X509Certificate[] chain = xkms[i].getCertificateChain(alias);
			if(chain!=null&&chain.length>0) {
				algo = chain[0].getSigAlgName(); 
				pub = chain[0].getEncoded();
				ByteArrayOutputStream baos = new ByteArrayOutputStream(pub.length*2); 
				ByteArrayInputStream bais = new ByteArrayInputStream(pub);
				Symm.base64noSplit.encode(bais,baos,X509);
				cert = baos.toString();
				
				/*
				// Test
				bais = new ByteArrayInputStream(baos.toByteArray());
				baos = new ByteArrayOutputStream(input.length*2);
				Symm.base64noSplit().decode(bais,baos,5);
				byte[] output = baos.toByteArray();
				String reconstitute = output.toString();
				System.out.println("ok");
				CertificateFactory certFactory;
				try {
					bais = new ByteArrayInputStream(output);
					certFactory = CertificateFactory.getInstance("X.509");
					X509Certificate x509 = (X509Certificate)certFactory.generateCertificate(bais);
					System.out.println(x509.toString());
				} catch (CertificateException e) {
					e.printStackTrace();
				}
				*/
			}
		}
		if(algo==null) throw new APIException("X509 Security Setter not configured");
	}

	@Override
	public void setSecurity(DME2Client dme2) throws CadiException {
	}
	
	/* (non-Javadoc)
	 * @see com.att.cadi.SecuritySetter#getID()
	 */
	@Override
	public String getID() {
		return alias;
	}

}
