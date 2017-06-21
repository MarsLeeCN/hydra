package com.theotakutech.model.cache.impl;

import java.util.ArrayList;
import java.util.List;

import com.theotakutech.finder.SetFinder;
import com.theotakutech.generator.set.CacheSetGenerator;
import com.theotakutech.model.cache.Cache;
import com.theotakutech.model.set.CacheSet;

public class ArrayListCacheData<K, V> extends Cache<K, V> {

	private List<CacheSet<K, V>> cachesets;

	public ArrayListCacheData(int capacity, SetFinder<K, V> setFinder, CacheSetGenerator<K, V> cacheSetGenerator) {
		super(capacity, setFinder, cacheSetGenerator);
		cachesets = new ArrayList<CacheSet<K, V>>(numOfSets);
	}

	@Override
	protected CacheSet<K, V> getCacheSet(int index) {
		return cachesets.get(index);
	}

	@Override
	protected void initialization() {
		for (int i = 0; i < numOfSets; i++) {
			cachesets.add(cacheSetGenerator.generate());
		}
	}
}
