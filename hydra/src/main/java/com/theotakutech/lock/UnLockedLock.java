package com.theotakutech.lock;
/**
 * This is an implementation that without any lock mechanism
 * 
 * {@link Lock}
 * 
 * @author Mars
 */
public class UnLockedLock implements Lock{

	@Override
	public <T> T doReadTransaction(TransactionOperator<T> transactionOperator) {
		return transactionOperator.execute();
	}

	@Override
	public <T> T doWriteTransaction(TransactionOperator<T> transactionOperator) {
		return transactionOperator.execute();
	}

}
