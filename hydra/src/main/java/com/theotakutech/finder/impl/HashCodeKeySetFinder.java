package com.theotakutech.finder.impl;

import com.theotakutech.finder.SetFinder;
import com.theotakutech.model.entry.Entry;

/**
 * This is an implementation that through the hash code of cache entity's key
 * decide which set will store it
 * 
 * {@link SetFinder}
 * 
 * @author Mars
 *
 * @param <K>
 */
public class HashCodeKeySetFinder<K, V> extends SetFinder<K, V> {

	@Override
	public int find(K key) {
		return key.hashCode() % capacity;
	}

	@Override
	public void onCached(Entry<K, V> entry) {

	}

	@Override
	public void onTouched(K key) {

	}

	@Override
	public void onRemoved(K key) {

	}
}
