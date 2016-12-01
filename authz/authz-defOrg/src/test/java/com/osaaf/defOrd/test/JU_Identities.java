/**
 * 
 */
package com.osaaf.defOrd.test;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.att.authz.env.AuthzEnv;
import com.att.authz.env.AuthzTrans;
import com.att.authz.local.AbsData.Reuse;
import com.osaaf.defOrg.Identities;
import com.osaaf.defOrg.Identities.Data;

/**
 *
 */
public class JU_Identities {

	private static final String DATA_IDENTITIES = "../opt/app/aaf/data/identities.dat";
	private static File fids;
	private static Identities ids;
	private static AuthzEnv env;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		env = new AuthzEnv();
		AuthzTrans trans = env.newTransNoAvg();
		// Note: utilize TimeTaken, from trans.start if you want to time.
		fids = new File(DATA_IDENTITIES);
		if(fids.exists()) {
			ids = new Identities(fids);
			ids.open(trans, 5000);
		} else {
			
			throw new Exception("Data File for Tests, \"" + DATA_IDENTITIES 
					+ "\" must exist before test can run. (Current dir is " + System.getProperty("user.dir") + ")");
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		AuthzTrans trans = env.newTransNoAvg();
		if(ids!=null) {
			ids.close(trans);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
 
	@Test
	public void test() throws IOException {
		Reuse reuse = ids.reuse(); // this object can be reused within the same thread.
		Data id = ids.find("osaaf",reuse);
		Assert.assertNotNull(id);
		System.out.println(id);

		id = ids.find("mmanager",reuse);
		Assert.assertNotNull(id);
		System.out.println(id);

		//TODO Fill out JUnit with Tests of all Methods in "Data id"
	}

}
