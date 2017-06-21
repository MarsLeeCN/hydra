package com.theotakutech.model.entry;

public class Entry<K, V> {

	protected K key;

	protected V value;

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
}
