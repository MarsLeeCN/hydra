package com.theotakutech.model.cache;

import java.util.HashSet;
import java.util.Set;

import com.theotakutech.finder.SetFinder;
import com.theotakutech.generator.set.CacheSetGenerator;
import com.theotakutech.listener.CacheListener;
import com.theotakutech.model.entry.Entry;
import com.theotakutech.model.set.CacheSet;

public abstract class Cache<K, V> {

	protected final int capacity;

	protected final SetFinder<K, V> setFinder;

	protected final CacheSetGenerator<K, V> cacheSetGenerator;

	protected final int numOfSets;

	private Set<CacheListener<Entry<K, V>, K, V>> listeners = new HashSet<>();

	private volatile boolean running;

	public Cache(int capacity, SetFinder<K, V> setFinder, CacheSetGenerator<K, V> cacheSetGenerator) {
		super();
		this.capacity = capacity;
		this.setFinder = setFinder;
		this.cacheSetGenerator = cacheSetGenerator;
		this.numOfSets = computeNumOfSets();
		this.setFinder.initialization(numOfSets);
	}

	public void addListener(CacheListener<Entry<K, V>, K, V> listener) {
		this.listeners.add(listener);
	}

	protected int computeNumOfSets() {
		if (capacity % cacheSetGenerator.getCapacity() == 0)
			return capacity / cacheSetGenerator.getCapacity();
		return capacity / cacheSetGenerator.getCapacity() + 1;
	}

	protected abstract CacheSet<K, V> getCacheSet(int index);

	protected int getCacheSetIndex(K key) {
		return setFinder.find(key);
	};

	public synchronized void start() {
		if (running) {
			throw new IllegalArgumentException("Cache is already running");
		}
		initialization();
		this.running = true;
	}

	protected abstract void initialization();

	public V get(K key) {
		int setIndex = getCacheSetIndex(key);
		return getCacheSet(setIndex).get(key);
	}

	public V set(Entry<K, V> entry) {
		int setIndex = getCacheSetIndex(entry.getKey());
		return getCacheSet(setIndex).set(entry);
	}
}
