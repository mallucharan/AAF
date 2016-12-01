/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
/**
 * Slot.java
 *
 * Created on: Dec 5, 2008
 * Created by: jg1555
 *
 * (c)2008 SBC Knowledge Ventures, L.P. All rights reserved.
 ******************************************************************* 
 * RESTRICTED - PROPRIETARY INFORMATION The Information contained 
 * herein is for use only by authorized employees of AT&T Services, 
 * Inc., and authorized Affiliates of AT&T Services, Inc., and is 
 * not for general distribution within or outside the respective 
 * companies. 
 *******************************************************************
 */
package com.att.inno.env;

/**
 * StaticSlot's are used to store and retrieve data from the Organizer that does not change.
 */
public final class StaticSlot {

	/*
	 * The name of the StaticSlot.
	 */
	private final String key;
	
	/*
	 * The index of the Organizer's static map associated with this StaticSlot.
	 */
	final int slot; 
	
	/**
	 * Constructs a new StaticSlot.
	 * 
	 * @param index
	 * 			The index of Organizer's static map this StaticSlot is associated with.
	 * @param name
	 * 			The name of the StaticSlot's key.
	 */
	StaticSlot(int index, String name) {
		slot = index;
		key = name;
	}
	
	/**
	 * Debug method only to print key=slot pairs.
	 */
	public String toString() {
		return key + '=' + slot;
	}
	
	/**
	 * Returns the name of this StaticSlot's key.
	 * 
	 * @return
	 * 			The name of this StaticSlot's key.
	 */
	public String getKey() {
		return key;
	}

}

