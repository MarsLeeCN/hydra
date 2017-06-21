package com.theotakutech;

import com.theotakutech.builder.CacheBuilder;
import com.theotakutech.model.cache.Cache;
import com.theotakutech.model.entry.Entry;

public class Example {
	public static void main(String[] args) {
		CacheBuilder<String, User> cacheBuilder = new CacheBuilder<String, User>();
		Cache<String, User> cache = cacheBuilder.build();
		for (int i = 0; i < 12; i++) {
			User user = new User();
			user.setId(i + "");
			user.setName("l");
			Entry<String, User> entry = new Entry<>();
			entry.setKey("" + i);
			entry.setValue(user);
			cache.set(entry);
		}
		System.out.println(cache.get("1").getName());
	}

}
