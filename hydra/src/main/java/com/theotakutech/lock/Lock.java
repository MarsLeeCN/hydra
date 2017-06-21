package com.theotakutech.lock;
/**
 * Define transaction interface to read or write the cache set
 * 
 * Lock implementation list:
 * <p>
 * {@link SimpleSynchronizedLock}
 * <p>
 * {@link ReadWriteSyncLock}
 * <p>
 * {@link StampedOptimisticSyncLock }
 * <p>
 * {@link StampedSyncLock }
 * <p>
 * Different locks are achieved in different environments with different
 * performance
 * <p>
 * Here are some test data for reference:
 * 
 * <p>
 * 5 read threads and 5 write treads
 * <li>SimpleSynchronizedLock 		209ms
 * <li>ReadWriteSyncLock 			82ms
 * <li>StampedOptimisticSyncLock 	345ms
 * <li>StampedSyncLock 				68ms
 * 
 * <p>
 * 10 read threads and 10 write treads
 * <li>SimpleSynchronizedLock 		185ms
 * <li>ReadWriteSyncLock 			2209ms
 * <li>StampedOptimisticSyncLock 	394ms
 * <li>StampedSyncLock 				180ms
 * 
 * <p>
 * 16 read threads and 4 write treads
 * <li>SimpleSynchronizedLock 		554ms
 * <li>ReadWriteSyncLock 			22663ms
 * <li>StampedOptimisticSyncLock 	461ms
 * <li>StampedSyncLock 				455ms
 *
  * <p>
 * 19 read threads and 1 write treads
 * <li>SimpleSynchronizedLock 		2173ms
 * <li>ReadWriteSyncLock 			116941ms
 * <li>StampedOptimisticSyncLock 	1167ms
 * <li>StampedSyncLock 				56850ms
 * @author Mars
 */
public interface Lock {

	public <T> T doReadTransaction(TransactionOperator<T> transactionOperator);

	public <T> T doWriteTransaction(TransactionOperator<T> transactionOperator);
}
