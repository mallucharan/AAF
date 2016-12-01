/*******************************************************************************
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 *******************************************************************************/
package com.att.rosetta.marshal;

import java.util.Iterator;

import com.att.rosetta.Ladder;
import com.att.rosetta.Marshal;
import com.att.rosetta.ParseException;
import com.att.rosetta.Parsed;

public class DocMarshal<T> extends Marshal<T> {
	private Marshal<T> root;
	
	public DocMarshal(Marshal<T> root) {
		this.root = root;
	}
	
	@Override
	public Parsed<State> parse(T t, Parsed<State> parsed) throws ParseException {
		Ladder<Iterator<?>> ladder = parsed.state.ladder;
		Iterator<?> iter = ladder.peek();
		if(iter==null) {
			ladder.push(PENDING_ITERATOR);
			parsed.event = START_DOC;
		} else if (DONE_ITERATOR.equals(iter)) {
		} else {
			ladder.ascend(); // look at field info
				Iterator<?> currFieldIter = ladder.peek();
				if(!DONE_ITERATOR.equals(currFieldIter)){
					parsed = root.parse(t, parsed);
				}
			ladder.descend();
			if(DONE_ITERATOR.equals(currFieldIter) || parsed.event==NONE) {
				parsed.event = END_DOC;
				ladder.push(DONE_ITERATOR);
			}
		}
		return parsed; // if unchanged, then it will end process

	}

	public static final Iterator<Void> PENDING_ITERATOR = new Iterator<Void>() {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Void next() {
			return null;
		}

		@Override
		public void remove() {
		}
	};

	public static<T> DocMarshal<T> root(Marshal<T> m) {
		return (DocMarshal<T>)new DocMarshal<T>(m);
	}

}
