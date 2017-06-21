package com.theotakutech.lock;
/**
 * 
 * This is an implementation that use OptimisticRead lock
 * 
 * {@link Lock}
 * 
 * {@link StampedLock}
 * 
 * @author Mars
 *
 */
public class StampedOptimisticSyncLock extends StampedSyncLock {

	@Override
	public <T> T doReadTransaction(TransactionOperator<T> transactionOperator) {
		long stamp = stampedLock.tryOptimisticRead();
		T t = transactionOperator.execute();
		if (!stampedLock.validate(stamp)) {
			try {
				stamp = stampedLock.readLock();
				t = transactionOperator.execute();
			} finally {
				stampedLock.unlockRead(stamp);
			}
		}
		return t;
	}
}
