package com.theotakutech.eviction;

import java.util.Set;

import com.theotakutech.listener.CacheListener;
import com.theotakutech.model.entry.Entry;

/**
 * 
 * Define eviction algorithm interface.
 * 
 * @author Mars
 * @param <K>
 *
 * @param <K>
 *            The key type of K-V cache
 * @param <V>
 */
public interface EvictionAlgo<K, V> extends CacheListener<Entry<K, V>, K, V> {
	/**
	 * When the cache set is full. can invoke this method get whose keys can be
	 * removed so that get more space
	 * 
	 * @return Set of keys who can be removed.
	 */
	public Set<K> evict();
}
