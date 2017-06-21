package com.theotakutech.finder;

import com.theotakutech.listener.CacheListener;
import com.theotakutech.model.entry.Entry;

/**
 * Through the key of cache entity decide which set will store it.
 * 
 * @author Mars
 * @param <K>
 *
 * @param <K>
 *            The key type of K-V cache
 * @param <V>
 * @param <V>
 */
public abstract class SetFinder<K, V> implements CacheListener<Entry<K, V>, K, V> {
	/**
	 * The number of cache sets.
	 */
	protected int capacity;

	/**
	 * Through the key get the index of sets
	 * 
	 * @param key
	 *            of cache entity
	 * @return
	 */
	public abstract int find(K key);

	public void initialization(int capacity) {
		this.capacity = capacity;
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
