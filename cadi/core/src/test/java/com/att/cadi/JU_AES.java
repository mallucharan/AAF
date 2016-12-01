/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.junit.Test;

import junit.framework.Assert;

public class JU_AES {

	@Test
	public void test() throws Exception {
		AES aes = new AES();
		String orig = "I'm a password, really";
		byte[] passin = orig.getBytes();
		byte[] encrypted = aes.encrypt(passin);
		byte[] b64enc = Symm.base64.encode(encrypted);
		System.out.println(new String(b64enc));
		
		encrypted = Symm.base64.decode(b64enc);
		passin = aes.decrypt(encrypted);
		Assert.assertEquals(orig, new String(passin));
	}

	@Test
	public void testInputStream() throws Exception {
		AES aes = new AES();
		String orig = "I'm a password, really";
		ByteArrayInputStream bais = new ByteArrayInputStream(orig.getBytes());
		CipherInputStream cis = aes.inputStream(bais, true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Symm.base64.encode(cis, baos);
		cis.close();

		byte[] b64encrypted;
		System.out.println(new String(b64encrypted=baos.toByteArray()));
		
		
		baos.reset();
		CipherOutputStream cos = aes.outputStream(baos, false);
		Symm.base64.decode(new ByteArrayInputStream(b64encrypted),cos);
		cos.close();
		Assert.assertEquals(orig, new String(baos.toByteArray()));
	}

	@Test
	public void testObtain() throws Exception {
		byte[] keygen = Symm.baseCrypt().keygen();
		
		Symm symm = Symm.obtain(new ByteArrayInputStream(keygen));
		
		String orig ="Another Password, please";
		String encrypted = symm.enpass(orig);
		System.out.println(encrypted);
		String decrypted = symm.depass(encrypted);
		System.out.println(decrypted);
		Assert.assertEquals(orig, decrypted);
	}

}
