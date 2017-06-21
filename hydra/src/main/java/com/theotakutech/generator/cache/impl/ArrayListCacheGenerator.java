package com.theotakutech.generator.cache.impl;

import com.theotakutech.finder.SetFinder;
import com.theotakutech.generator.cache.CacheGenerator;
import com.theotakutech.generator.set.CacheSetGenerator;
import com.theotakutech.model.cache.Cache;
import com.theotakutech.model.cache.impl.ArrayListCacheData;

public class ArrayListCacheGenerator<K, V> extends CacheGenerator<K, V> {

	public ArrayListCacheGenerator(int capacity, SetFinder<K, V> setDiscoverer,
			CacheSetGenerator<K, V> cacheSetGenerator) {
		super(capacity, setDiscoverer, cacheSetGenerator);
	}

	@Override
	public Cache<K, V> generate() {
		Cache<K, V> cache = new ArrayListCacheData<>(capacity, setFinder, cacheSetGenerator);
		return cache;
	}
}
