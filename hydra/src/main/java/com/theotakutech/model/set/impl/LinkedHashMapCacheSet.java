package com.theotakutech.model.set.impl;

import java.util.LinkedHashMap;

import com.theotakutech.eviction.EvictionAlgo;
import com.theotakutech.lock.Lock;
import com.theotakutech.model.set.CacheSet;

public class LinkedHashMapCacheSet<K, V> extends CacheSet<K, V> {

	private final LinkedHashMap<K, V> cache;

	public LinkedHashMapCacheSet(Lock lock, int capacity, EvictionAlgo<K, V> evictionAlgo) {
		super(lock, capacity, evictionAlgo);
		cache = new LinkedHashMap<>(capacity);
	}

	@Override
	protected boolean isFull() {
		return cache.size() >= capacity;
	}

	@Override
	protected V setCacheLine(K k, V v) {
		cache.put(k, v);
		return v;
	}

	@Override
	protected boolean containsKey(K k) {
		return cache.containsKey(k);
	}

	@Override
	protected V getCacheLine(K key) {
		return cache.get(key);
	}

	@Override
	protected void removeCacheLine(K key) {
		cache.remove(key);
	}
}
