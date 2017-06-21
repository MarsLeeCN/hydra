package com.theotakutech.generator.set.impl;

import com.theotakutech.eviction.EvictionAlgo;
import com.theotakutech.generator.Generator;
import com.theotakutech.generator.set.CacheSetGenerator;
import com.theotakutech.lock.Lock;
import com.theotakutech.model.set.CacheSet;
import com.theotakutech.model.set.impl.LinkedHashMapCacheSet;

public class LinkedHashMapCacheSetGenerator<K, V> extends CacheSetGenerator<K, V> {

	public LinkedHashMapCacheSetGenerator(int capacity, Generator<EvictionAlgo<K, V>> evictionAlgoGenerator,
			Generator<Lock> lockGenerator) {
		super(capacity, evictionAlgoGenerator, lockGenerator);
	}

	@Override
	public CacheSet<K, V> generate() {
		return new LinkedHashMapCacheSet<>(lockGenerator.generate(), capacity, evictionAlgoGenerator.generate());
	}

}
