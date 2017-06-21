package com.theotakutech.lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * This is an implementation that use ReentrantReadWriteLock
 * 
 * {@link Lock}
 * 
 * {@link ReentrantReadWriteLock}
 * 
 * @author Mars
 *
 */
public class ReadWriteSyncLock implements Lock {

	private final ReadWriteLock lock;

	public ReadWriteSyncLock(boolean fair) {
		super();
		this.lock = new ReentrantReadWriteLock(fair);
	}

	@Override
	public <T> T doReadTransaction(TransactionOperator<T> transactionOperator) {
		lock.readLock().lock();
		try {
			return transactionOperator.execute();
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public <T> T doWriteTransaction(TransactionOperator<T> transactionOperator) {
		lock.writeLock().lock();
		try {
			return transactionOperator.execute();
		} finally {
			lock.writeLock().unlock();
		}
	}

}
