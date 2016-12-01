/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.xgen.html;

import java.util.ArrayList;
import java.util.List;

public class Imports implements Thematic{
	List<String> css,js;
	public final int backdots;
//	public final File webDir;
	private String theme;
	
	public Imports(int backdots) {
//		this.webDir = webDir;
		
		css = new ArrayList<String>();
		js = new ArrayList<String>();
		this.backdots = backdots;
		theme = "";
	}
	
	public Imports css(String str) {
		css.add(str);
		return this;
	}
	
	public Imports js(String str) {
		js.add(str);
		return this;
	}

	public Imports theme(String str) {
		theme = str==null?"":str;
		return this;
	}

	/**
	 * Pass in a possible Theme.  If it is "" or null, it will resolve to default Theme set in Imports
	 * 
	 * @param theTheme
	 * @return
	 */
	@Override
	public String themePath(String theTheme) {
		StringBuilder src = dots(new StringBuilder());
		if(theTheme==null||theTheme.length()==0) {
			src.append(theme);
			if(theme.length()>0)src.append('/');
		} else {
			src.append(theTheme);
			src.append('/');
		}

		return src.toString();
	}
	
	/**
	 * Pass in a possible Theme.  If it is "" or null, it will resolve to default Theme set in Imports
	 * 
	 * @param theTheme
	 * @return
	 */
	@Override
	public String themeResolve(String theTheme) {
		return (theTheme==null||theTheme.length()==0)
			?theme
			:theTheme;
	}

	public StringBuilder dots(StringBuilder src) {
		for(int i=0;i<backdots;++i) {
			src.append("../");
		}
		return src;
	}
	
};

