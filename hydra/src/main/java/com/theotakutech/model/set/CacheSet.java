package com.theotakutech.model.set;

import java.util.HashSet;
import java.util.Set;

import com.theotakutech.eviction.EvictionAlgo;
import com.theotakutech.listener.CacheListener;
import com.theotakutech.lock.Lock;
import com.theotakutech.model.entry.Entry;

public abstract class CacheSet<K, V> {

	protected final int capacity;

	protected final Set<CacheListener<Entry<K, V>, K, V>> listeners = new HashSet<>();

	protected final EvictionAlgo<K, V> evictionAlgo;

	protected final Lock lock;

	public CacheSet(Lock lock, int capacity, EvictionAlgo<K, V> evictionAlgo) {
		this.lock = lock;
		this.capacity = capacity;
		this.evictionAlgo = evictionAlgo;
		this.listeners.add(evictionAlgo);
	}

	protected V setV(Entry<K, V> e) {
		V value = null;
		if (isFull() && !containsKey(e.getKey())) {
			Set<K> expellableKeys = evictionAlgo.evict();
			removeCacheLines(expellableKeys);
		}
		value = setCacheLine(e.getKey(), e.getValue());
		for (CacheListener<Entry<K, V>, K, V> listener : listeners) {
			listener.onCached(e);
		}
		return value;
	}

	protected V getV(K key) {
		for (CacheListener<Entry<K, V>, K, V> listener : listeners) {
			listener.onTouched(key);
		}
		return getCacheLine(key);
	}

	protected abstract boolean isFull();

	protected abstract boolean containsKey(K k);

	protected void removeCacheLines(Set<K> keys) {
		for (K key : keys) {
			for (CacheListener<Entry<K, V>, K, V> listener : listeners) {
				listener.onRemoved(key);
			}
		}
	}

	protected abstract V setCacheLine(K k, V v);

	protected abstract V getCacheLine(K key);

	protected abstract void removeCacheLine(K key);

	public V get(K key) {
		return lock.doReadTransaction(() -> getV(key));
	}
	
	public V set(Entry<K, V> e) {
		return lock.doWriteTransaction(() -> setV(e));
	}
}
