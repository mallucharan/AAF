/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.att.cadi.util.Chmod;

public class AES {
	public static final String AES = AES.class.getSimpleName();
	public static final int AES_KEY_SIZE = 128; // 256 isn't supported on all JDKs.
	
	private Cipher aesCipher;
	private SecretKeySpec aeskeySpec;

	public AES() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException {
		aesCipher = Cipher.getInstance(AES);
	    aeskeySpec = new SecretKeySpec(newKey().getEncoded(), AES);
	}
	
	public static SecretKey newKey() throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance(AES);
	    kgen.init(AES_KEY_SIZE);
	    return kgen.generateKey();
	}

	public AES(File keyfile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException {
		aesCipher = Cipher.getInstance(AES);
		byte[] aesKey = new byte[AES_KEY_SIZE/8];
		FileInputStream fis = new FileInputStream(keyfile);
		try {
			fis.read(aesKey);
		} finally {
			fis.close();
		}
		aeskeySpec = new SecretKeySpec(aesKey,AES);
	}

	public AES(byte[] aeskey, int offset, int len) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException {
		aesCipher = Cipher.getInstance(AES);
		aeskeySpec = new SecretKeySpec(aeskey,offset,len,AES);
	}
	
	public byte[] encrypt(byte[] in) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		aesCipher.init(Cipher.ENCRYPT_MODE,aeskeySpec);
		return aesCipher.doFinal(in);
	}
	
	public byte[] decrypt(byte[] in) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		aesCipher.init(Cipher.DECRYPT_MODE,aeskeySpec); 
		return aesCipher.doFinal(in);
	}
	
	public void save(File keyfile) throws IOException {
		FileOutputStream fis = new FileOutputStream(keyfile);
		try {
			fis.write(aeskeySpec.getEncoded());
		} finally {
			fis.close();
		}
		Chmod.to400.chmod(keyfile);
	}

	public CipherOutputStream outputStream(OutputStream os, boolean encrypt) {
		try {
			if(encrypt) {
				aesCipher.init(Cipher.ENCRYPT_MODE,aeskeySpec);
			} else {
				aesCipher.init(Cipher.DECRYPT_MODE,aeskeySpec);
			}
		} catch (InvalidKeyException e) {
			// KeySpec created earlier... no chance being wrong.
		} 
		return new CipherOutputStream(os,aesCipher);
	}
	
	public CipherInputStream inputStream(InputStream is, boolean encrypt) {
		try {
			if(encrypt) {
				aesCipher.init(Cipher.ENCRYPT_MODE,aeskeySpec);
			} else {
				aesCipher.init(Cipher.DECRYPT_MODE,aeskeySpec);
			}
		} catch (InvalidKeyException e) {
			// KeySpec created earlier... no chance being wrong.
		} 
		
		return new CipherInputStream(is,aesCipher);
	}
}
