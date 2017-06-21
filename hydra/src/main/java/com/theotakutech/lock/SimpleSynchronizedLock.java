package com.theotakutech.lock;
/**
 * This is an implementation that use built-in sync mechanism
 * 
 * {@link Lock}
 * 
 * @author Mars
 *
 */
public class SimpleSynchronizedLock implements Lock {

	@Override
	public <T> T doReadTransaction(TransactionOperator<T> transactionOperator) {
		synchronized (this) {
			return transactionOperator.execute();
		}
	}

	@Override
	public <T> T doWriteTransaction(TransactionOperator<T> transactionOperator) {
		synchronized (this) {
			return transactionOperator.execute();
		}
	}

}
