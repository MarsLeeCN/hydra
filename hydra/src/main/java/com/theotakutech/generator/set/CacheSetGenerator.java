package com.theotakutech.generator.set;

import com.theotakutech.eviction.EvictionAlgo;
import com.theotakutech.generator.Generator;
import com.theotakutech.lock.Lock;
import com.theotakutech.model.set.CacheSet;

public abstract class CacheSetGenerator<K, V> implements Generator<CacheSet<K, V>> {

	protected int capacity;

	protected Generator<EvictionAlgo<K, V>> evictionAlgoGenerator;

	protected Generator<Lock> lockGenerator;

	public CacheSetGenerator(int capacity, Generator<EvictionAlgo<K, V>> evictionAlgoGenerator,
			Generator<Lock> lockGenerator) {
		super();
		this.capacity = capacity;
		this.evictionAlgoGenerator = evictionAlgoGenerator;
		this.lockGenerator = lockGenerator;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setEvictionAlgoGenerator(Generator<EvictionAlgo<K, V>> evictionAlgoGenerator) {
		this.evictionAlgoGenerator = evictionAlgoGenerator;
	}

	public void setLockGenerator(Generator<Lock> lockGenerator) {
		this.lockGenerator = lockGenerator;
	}

	public int getCapacity() {
		return capacity;
	}
}
