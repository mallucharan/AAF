/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;

import java.util.Map;

public interface EnvProps extends Env {
	public interface EnvProperty {
		public String getProperty(String input);
	};

	/**
	 * Obtain a Property (String) based on a Key.  Implementor decides how
	 * that works, i.e. from a complex set of Configurations, or just 
	 * "System" (Java standard)
	 * 
	 * @param key
	 * @return APIException
	 */
	public String getProperty(String key);

	/**
	 * Obtain a Property (String) based on a Key.  Implementor decides how
	 * that works, i.e. from a complex set of Configurations, or just 
	 * "System" (Java standard)
	 * 
	 * If Property Value is null, then default will be used.
	 * @param key
	 * @return APIException
	 */
	public String getProperty(String tag, String defaultValue);

	/**
	 * Set a Property (String) based on a Key accessible to all in Env.  Implementor decides how
	 * that works, i.e. from a complex set of Configurations, or just 
	 * "System" (Java standard)
	 * 
	 * @param key
	 * @return APIException
	 */
	public String setProperty(String key, String value);
	
	/**
	 * Get the SubProperties based on key.
	 * 
	 * use "false" to remove prefix, "true" to leave prefix in.
	 * 
	 * @param key
	 * @return APIException
	 * Given a known property set (or in this case, properties starting with key), 
	 * return map of all properties with appropriate key names
	 */
	public Map<String, String> getSubProperties(String key, boolean includePrefix);

	/**
	 * Get all of the properties in the Environment
	 * @return
	 */
	public Map<String, String> getProperties();

}
