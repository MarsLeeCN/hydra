package com.theotakutech.finder.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.theotakutech.finder.SetFinder;

/**
 * This is an implementation that a cache set stores the cache entity one by one
 * to ensure cache entities will distribute evenly into each cache set. but this
 * way will have some impact on performance
 * 
 * {@link SetFinder}
 * 
 * @author Mars
 *
 * @param <K>
 */
public class BalanceSetFinder<K, V> extends SetFinder<K, V> {

	private AtomicInteger counter = new AtomicInteger(0);

	private Map<K, Integer> keyIndexMap = new HashMap<K, Integer>();

	@Override
	public int find(K key) {
		if (keyIndexMap.containsKey(key)) {
			return keyIndexMap.get(key).intValue();
		} else {
			synchronized (this) {
				if (keyIndexMap.containsKey(key)) {
					return keyIndexMap.get(key).intValue();
				}
				int count = counter.incrementAndGet();
				int index = count % capacity;
				keyIndexMap.put(key, index);
				return index;
			}
		}
	}

}
