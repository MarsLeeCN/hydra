package com.theotakutech.builder;

import com.theotakutech.eviction.EvictionAlgo;
import com.theotakutech.eviction.impl.MRUCacheLineEvictionAlgo;
import com.theotakutech.finder.SetFinder;
import com.theotakutech.finder.impl.HashCodeKeySetFinder;
import com.theotakutech.generator.Generator;
import com.theotakutech.generator.cache.CacheGenerator;
import com.theotakutech.generator.cache.impl.ArrayListCacheGenerator;
import com.theotakutech.generator.lock.LockGenerator;
import com.theotakutech.generator.set.CacheSetGenerator;
import com.theotakutech.generator.set.impl.LinkedHashMapCacheSetGenerator;
import com.theotakutech.lock.Lock;
import com.theotakutech.lock.StampedOptimisticSyncLock;
import com.theotakutech.model.cache.Cache;

public class CacheBuilder<K, V> {

	private CacheGenerator<K, V> cacheGenerator;
	private CacheSetGenerator<K, V> cacheSetGenerator;

	private final int DEFAULT_CAPACITY = 10;

	private final int DEFAULT_CACHE_SET_CAPACITY = 10;

	private final int DEFAULT_EVICT_BUFFER = 2;

	public CacheBuilder() {
		super();
		defaultSetUp();
	}

	public CacheBuilder(int cacheSetCapacity, int capacity) {
		this();
		setCacheSetCapacity(cacheSetCapacity);
		setCacheCapacity(capacity);
	}

	private void defaultSetUp() {
		SetFinder<K, V> setFinder = new HashCodeKeySetFinder<>();
		cacheGenerator = new ArrayListCacheGenerator<K, V>(DEFAULT_CAPACITY, setFinder, cacheSetGenerator);
		cacheSetGenerator = new LinkedHashMapCacheSetGenerator<K, V>(DEFAULT_CACHE_SET_CAPACITY,
				new Generator<EvictionAlgo<K, V>>() {
					@Override
					public EvictionAlgo<K, V> generate() {
						return new MRUCacheLineEvictionAlgo<K, V>(DEFAULT_EVICT_BUFFER);
					}
				}, new Generator<Lock>() {
					@Override
					public Lock generate() {
						return new StampedOptimisticSyncLock();
					}
				});
	}

	public CacheBuilder<K, V> setCacheCapacity(int capacity) {
		cacheGenerator.setCapacity(capacity);
		return this;
	}

	public CacheBuilder<K, V> setCacheSetCapacity(int cacheSetCapacity) {
		cacheSetGenerator.setCapacity(cacheSetCapacity);
		return this;
	}

	public CacheBuilder<K, V> setGenerator(CacheGenerator<K, V> cacheGenerator) {
		this.cacheGenerator = cacheGenerator;
		return this;
	}

	public CacheBuilder<K, V> setGenerator(CacheSetGenerator<K, V> cacheSetGenerator) {
		this.cacheSetGenerator = cacheSetGenerator;
		return this;
	}

	public CacheBuilder<K, V> setCacheSetLockGenerator(LockGenerator lockGenerator) {
		this.cacheSetGenerator.setLockGenerator(lockGenerator);
		return this;
	}

	public CacheBuilder<K, V> setCacheSetEvictionAlgoGenerator(Generator<EvictionAlgo<K, V>> evictionAlgoGenerator) {
		this.cacheSetGenerator.setEvictionAlgoGenerator(evictionAlgoGenerator);
		return this;
	}

	public CacheBuilder<K, V> setCacheSetDiscoverer(SetFinder<K, V> setDiscoverer) {
		this.cacheGenerator.setSetDiscoverer(setDiscoverer);
		return this;
	}

	public Cache<K, V> build() {
		cacheGenerator.setCacheSetGenerator(cacheSetGenerator);
		Cache<K, V> cache = cacheGenerator.generate();
		cache.start();
		return cache;
	}
}
