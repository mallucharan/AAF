/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.data.test;

import java.io.IOException;
import java.io.Writer;

import com.att.inno.env.Trans;
import com.att.inno.env.Trans.Metric;

public class Report {
	float total;
	float buckets[];
	String[] names;
	private int iterations;
	private int count;
	
	public Report(int iters, String ... names) {
		iterations = iters;
		buckets = new float[names.length];
		this.names = names;
		total=0;
		count = 0;
	}
	
	public void glean(Trans trans, int ... type) {
		Metric m = trans.auditTrail(0, null, type);
		total+=m.total;
		int min = Math.min(buckets.length, m.buckets.length);
		for(int b=0;b<min;++b) {
			buckets[b]+=m.buckets[b];
		}
	}
	
	public boolean go() {
		return ++count<iterations;
	}
	
	
	public void report(Writer sbw) throws IOException {
		sbw.append("\n"+count + " entries, Total Time: " + total + "ms, Avg Time: " + total/count + "ms\n");
		int min = Math.min(buckets.length, names.length);
		for(int i=0;i<min;++i) {
			sbw.append("  Time: " + names[i] + ' ' + buckets[i] + "ms, Avg Time: " + buckets[i]/count + "ms\n");
		}

	}
}
