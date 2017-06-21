package com.theotakutech.generator;
/**
 * Define a generator that will create a model instance
 * 
 * @author Mars
 *
 * @param <S> return a instance of what will be created
 */
public interface Generator<S> {

	public S generate();
}
