/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta.marshal;

import java.util.Iterator;
import java.util.List;

/**
 * Need an Iterator that can peek the current value without changing
 *
 * @param <T>
 */
final class ListIterator<T> implements Iterator<T> {
	private T curr;
	private Iterator<T> delg;
	public ListIterator(List<T> list) {
		curr = null;
		delg = list.iterator(); 
	}
	@Override
	public boolean hasNext() {
		return delg.hasNext();
	}

	@Override
	public T next() {
		return curr = delg.hasNext()?delg.next():null;
	}
	
	public T peek() {
		return curr==null?next():curr;
	}

	@Override
	public void remove() {
		delg.remove();
	}
	
}