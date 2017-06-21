package com.theotakutech.eviction.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.theotakutech.eviction.EvictionAlgo;
import com.theotakutech.model.entry.Entry;

/**
 * 
 * The least recently used eviction algorithm implementation
 * 
 * @author Mars
 *
 *         {@link EvictionAlgo}
 *
 * @param <K>
 *            The key type of K-V cache
 * @param <V>
 */
public class LRUCacheLineEvictionAlgo<K, V> implements EvictionAlgo<K, V> {

	/**
	 * Cache keys of entities that stored in one cache set
	 */
	protected LinkedList<K> keys = new LinkedList<K>();

	/**
	 * How many keys will be evict every time
	 */
	protected final int evictBuffer;

	public LRUCacheLineEvictionAlgo(int evictBuffer) {
		super();
		this.evictBuffer = evictBuffer;
	}

	@Override
	public Set<K> evict() {
		Set<K> willRemove = new HashSet<>();
		for (int i = 0; i < evictBuffer && keys.size() > 0; i++) {
			keys.add(keys.removeLast());
		}
		return willRemove;
	}

	@Override
	public void onCached(Entry<K, V> entry) {
		onTouched(entry.getKey());
	}

	@Override
	public void onTouched(K key) {
		keys.remove(key);
		keys.addFirst(key);
	}

	@Override
	public void onRemoved(K key) {
		keys.remove(key);
	}
}
