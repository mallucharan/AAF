/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.inno.env;

public interface TransStore extends Trans {
	/**
	 * Returns the Slot assigned to the supplied name.
	 * 
	 * @param name
	 * 			The name of the Slot to acquire.
	 * @return
	 * 			The Slot associated with the supplied name.
	 */
	public abstract Slot slot(String name);
	
	/**
	 * Put data into the right slot 
	 */
	public void put(Slot slot, Object value);

	/**
	 *  Get data from the right slot
	 *  
	 *  This will do a cast to the expected type derived from Default
	 */
	public<T> T get(Slot slot, T deflt);

	/**
	 * Returns an Object from the Organizer's static state, or the Default if null
	 * 
	 * @param slot
	 * 			The StaticSlot to retrieve the data from.
	 * @return
	 * 			The Object located in the supplied StaticSlot of the Organizer's static state.
	 */
	public abstract<T> T get(StaticSlot slot, T dflt);
	
}
