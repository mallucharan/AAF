/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.cadi;

import java.net.URI;

public interface Locator {
	public URI get(Locator.Item item) throws LocatorException;
	public boolean hasItems();
	public void invalidate(Locator.Item item) throws LocatorException;
	public Locator.Item best() throws LocatorException;
	public Item first() throws LocatorException;
	public Item next(Item item) throws LocatorException;
	public boolean refresh();
	
	public interface Item {}

}
