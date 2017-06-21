package com.theotakutech.generator.cache;

import com.theotakutech.finder.SetFinder;
import com.theotakutech.generator.Generator;
import com.theotakutech.generator.set.CacheSetGenerator;
import com.theotakutech.model.cache.Cache;

public abstract class CacheGenerator<K, V> implements Generator<Cache<K, V>> {

	protected int capacity;

	protected SetFinder<K, V> setFinder;

	protected CacheSetGenerator<K, V> cacheSetGenerator;

	public CacheGenerator(int capacity, SetFinder<K, V> setFinder, CacheSetGenerator<K, V> cacheSetGenerator) {
		super();
		this.capacity = capacity;
		this.setFinder = setFinder;
		this.cacheSetGenerator = cacheSetGenerator;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setSetDiscoverer(SetFinder<K, V> setDiscoverer) {
		this.setFinder = setDiscoverer;
	}

	public void setCacheSetGenerator(CacheSetGenerator<K, V> cacheSetGenerator) {
		this.cacheSetGenerator = cacheSetGenerator;
	}
}
