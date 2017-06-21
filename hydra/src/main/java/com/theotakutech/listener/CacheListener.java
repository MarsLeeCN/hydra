package com.theotakutech.listener;

import com.theotakutech.model.entry.Entry;

public interface CacheListener<E extends Entry<K, V>, K, V> {
	/**
	 * When the cache adds a new entity with a key whether save or update will
	 * trigger this method
	 * 
	 * @param k
	 *            The key of K-V cache
	 */
	public void onCached(E entry);

	/**
	 * When the cache get one entity or update one entity will trigger this
	 * method
	 * 
	 * @param k
	 */
	public void onTouched(K key);

	/**
	 * When the cache remove one entity will trigger this method
	 * 
	 * @param k
	 */
	public void onRemoved(K key);
}
