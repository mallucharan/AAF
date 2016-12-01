/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen.html;

import java.io.Writer;

import com.att.xgen.Mark;

public class HTML4Gen extends HTMLGen {
	private final static String DOCTYPE = 
		/*
		"<!DOCTYPE HTML PUBLIC " +
		"\"-//W3C//DTD HTML 4.01 Transitional//EN\" " +
		"\"http://www.w3.org/TR/html3/loose.dtd\">";
		"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"" +
		" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
		*/
		"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"" +
		" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";

	public HTML4Gen(Writer w) {
		super(w);
	}

	@Override
	public HTMLGen html(String ... attrib) {
		forward.println(DOCTYPE);
		return incr("html","xmlns=http://www.w3.org/1999/xhtml","xml:lang=en","lang=en");
		
	}

	@Override
	public Mark head() {
		Mark head = new Mark("head");
		incr(head);
		return head;
	}

	@Override
	public Mark body(String ... attrs) {
		Mark body = new Mark("body");
		incr(body,"body",attrs);
		return body;
	}
	
	@Override
	public HTML4Gen charset(String charset) {
		forward.append("<meta http-equiv=\"Content-type\" content=\"text.hml; charset=");
		forward.append(charset);
		forward.append("\">");
		prettyln(forward);
		return this;
	}

	@Override
	public Mark header(String ... attribs) {
		String[] a = new String[attribs.length+1];
		a[0]="header";
		System.arraycopy(attribs, 0, a, 1, attribs.length);
		return divID(a);
	}

	@Override
	public Mark footer(String ... attribs) {
		String[] a = new String[attribs.length+1];
		a[0]="footer";
		System.arraycopy(attribs, 0, a, 1, attribs.length);
		return divID(a);
	}

	@Override
	public Mark section(String ... attribs) {
		String[] a = new String[attribs.length+1];
		a[0]="section";
		System.arraycopy(attribs, 0, a, 1, attribs.length);
		return divID(a);
	}

	@Override
	public Mark article(String ... attribs) {
		String[] a = new String[attribs.length+1];
		a[0]="attrib";
		System.arraycopy(attribs, 0, a, 1, attribs.length);
		return divID(a);
	}

	@Override
	public Mark aside(String ... attribs) {
		String[] a = new String[attribs.length+1];
		a[0]="aside";
		System.arraycopy(attribs, 0, a, 1, attribs.length);
		return divID(a);
	}

	@Override
	public Mark nav(String ... attribs) {
		String[] a = new String[attribs.length+1];
		a[0]="nav";
		System.arraycopy(attribs, 0, a, 1, attribs.length);
		return divID(a);
	}

//	@Override
//	protected void importCSS(Imports imports) {
//		if(imports.css.size()==1) {
//			cssInline(imports.css.get(0));
//		} else {
//			text("<style type=\"text/css\">");
//			prettyln(forward);
//			forward.inc();
//			for(String str : imports.css) {
//				forward.print("@import url(\"");
//				forward.print(imports.themePath(null));
//				forward.print(str);
//				forward.print("\");");
//				prettyln(forward);
//			}
//			forward.dec();
//			forward.print("</style>");
//			prettyln(forward);
//		}
//	}
	
}
