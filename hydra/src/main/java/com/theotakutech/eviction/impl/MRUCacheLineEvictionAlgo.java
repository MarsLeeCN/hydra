package com.theotakutech.eviction.impl;

import java.util.HashSet;
import java.util.Set;

/**
 * The most recently used eviction algorithm implementation
 * 
 * @author Mars
 *
 * @param <K>
 */
public class MRUCacheLineEvictionAlgo<K, V> extends LRUCacheLineEvictionAlgo<K, V> {

	public MRUCacheLineEvictionAlgo(int evictBuffer) {
		super(evictBuffer);
	}

	@Override
	public Set<K> evict() {
		Set<K> willRemove = new HashSet<>();
		for (int i = 0; i < evictBuffer && keys.size() > 0; i++) {
			willRemove.add(keys.removeFirst());
		}
		return willRemove;
	}
}
