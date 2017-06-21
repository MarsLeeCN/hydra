package com.theotakutech.lock;
import java.util.concurrent.locks.StampedLock;
/**
 * This is an implementation that use StampedLock
 * 
 * {@link Lock}
 * 
 * {@link StampedLock}
 * 
 * @author Mars
 *
 */
public class StampedSyncLock implements Lock {

	StampedLock stampedLock = new StampedLock();

	@Override
	public <T> T doReadTransaction(TransactionOperator<T> transactionOperator) {
		long stamp = stampedLock.readLock();
		try {
			return transactionOperator.execute();
		} finally {
			stampedLock.unlockRead(stamp);
		}
	}

	@Override
	public <T> T doWriteTransaction(TransactionOperator<T> transactionOperator) {
		long stamp = stampedLock.writeLock();
		try {
			return transactionOperator.execute();
		} finally {
			stampedLock.unlockWrite(stamp);
		}
	}

}
