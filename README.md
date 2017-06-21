# Hydra Cache System
**Hydra is a N-way set associative cache.**

**The following pic tellls about hydra cache design:**
![](./design.png)

**Hydra cache supports custom cache set,cache set block,cache set finder and eviction algo.**

##Install
**Hydra required JDK 1.8+ and build required gradle 3.1+.**

**Download the hydra code and cd the project folder then execute following command:**

~~~
cd ./hydra
gradle build

...

ls
cd ./build/libs
~~~
**You could get the jar file from libs folder.than you could add this jar file into your project to use.**


##Use case
**Following is a demo to use Hydra to cache the user with the user's name as key**

**User model**

~~~
public class User {

	private String id;

	private String name;

	private String phoneNo;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phoneNo=" + phoneNo + "]";
	}
}
~~~

**Use cache**

~~~
	public static void main(String[] args) {
		CacheBuilder<String, User> cacheBuilder = new CacheBuilder<String, User>();
		Cache<String, User> cache = cacheBuilder.build();
		for (int i = 0; i < 12; i++) {
			User user = new User();
			user.setId(String.valueOf(i));
			user.setName("name" + i);
			cache.set(user.getName(), user);
		}
		System.out.println(cache.get("name1"));
	}
~~~
**As above case we put 12 users into the cache and set their name as cache key , the demo output :**

~~~
User [id=1, name=name1, phoneNo=null]
~~~

**In Cache builder already set up some default parameters**

* **CacheGenerator** : Defines cache generator that going to create a cache instance. It determines which cache implementation will be instantiated. Default is ArrayListCacheGenerator.
* **CacheSetGenerator** : Defines cache set generator, it going to create many cache sets of cache needed. Default is LinkedHashMapCacheSetGenerator.
* **SetFinder** : It locates in a cache generator. Defines how to find the cache set which to cache the entry through the key. Default is HashCodeKeySetFinder
* **Capacity** : Defines how many entries can be cached in the cache system. Default is 10.
* **CacheNumOfWasys** : Defines how many cache sets will be created. Default is 10.
* **LockGenerator** : Defines which lock will be set up for each cache set. Default is StampedOptimisticSyncLock.
* **EvictionAlgoGenerator** Defines wich eviction algo will be set up into each cache set. Default is MRUCacheLineEvictionAlgo.
*  **IndexEngine** Defines a component that can cache query conditions and cache keys so that can support search cache entry by query conditions. Default is null.

**If you do not want to use cache builder. you may can create a cache instance like following** :

~~~
public class Example {
	public static void main(String[] args) {
		int capacity = 100;
		int cacheSetCapacity = 5;
		int evictBuffer = 2;
		SetFinder<String> setFinder = new BalanceSetFinder<>();
		CacheSetGenerator<String, String> cacheSetGenerator = new LinkedHashMapCacheSetGenerator<>(capacity, () -> new LRUCacheLineEvictionAlgo<>(evictBuffer), () -> new UnLockedLock());
		cacheSetGenerator.setCapacity (cacheSetCapacity );
		ArrayListCacheData<String, String> cache = new ArrayListCacheData<>(capacity, setFinder, cacheSetGenerator);
		cache.start();
		//Test
		cache.set("testkey", "testv");
		System.out.println(cache.get("testkey"));
	}
}
~~~

## Core Components

**Cache**

Defines as a container contains all cache set. And it defines the cache system data storage structure. And you can use different implemention to set up different container as its cache sets container.

* **Cache implemention** :

	* **ArrayListCacheData** is one of cache implementions. It uses JDK ArrayList as storage container for cache sets.

**CacheSet**

Defines as a container contains all cache entries. All of cache sets just locate into one cache container.
You can even implement disk storage instead of memory.

* **Cache implemention** :
	* **LinkedHashMapCacheSet** is one of cache set implementions. It uses JDK LinkedHashMap as storage container for cache entries.

**Lock**

Defines lock mechanism to manage cache entry get and set operation to ensure consistency of transaction . Each cache set has its own lock and they are relatively independent for each cache set.

* **Lock implemention** :
	* **UnLockedLock** is one of lock implementions. It has no any  sync mechanism. You can use it if you do not need to any mechanism to ensure your operation is thread safe.
	* **ReadWriteSyncLock** is one of lock implementions. It uses JDK ReadWriteLock to implement the locking mechanism.
	* **SimpleSynchronizedLock** is one of lock implementions. It uses Java built in synchronization mechanism to ensure consistency of transaction. There is only one thread to do get or set operation  at same time.
	* **StampedSyncLock** is one of lock implementions. It uses JDK StampedLock to implement the locking mechanism.
	*  **StampedOptimisticSyncLock** is one of lock implementions. It uses JDK StampedLock with tryOptimisticRead method to implement the locking mechanism.

* **Tips**:

	Here are some test data to help you choose one of lock implementions in different use case.
	
	* 5 read threads and 5 write treads

		SimpleSynchronizedLock 		209ms
		
		ReadWriteSyncLock 			82ms
		
		StampedOptimisticSyncLock 	345ms
		
		StampedSyncLock 				68ms

	* 10 read threads and 10 write treads

		SimpleSynchronizedLock 		185ms
		
		ReadWriteSyncLock 			2209ms
		
		StampedOptimisticSyncLock 	394ms
		
		StampedSyncLock 				180ms

	* 16 read threads and 4 write treads
		
		SimpleSynchronizedLock 		554ms
		
		ReadWriteSyncLock 			22663ms
		
		StampedOptimisticSyncLock 	461ms
		
		StampedSyncLock 				455ms
	* 19 read threads and 1 write treads
		
		SimpleSynchronizedLock 		2173ms
		
		ReadWriteSyncLock 			116941ms
		
		StampedOptimisticSyncLock 	1167ms
		
		StampedSyncLock 				56850ms
		
**EvictionAlgo**

Defines eviction algo that to determine which cache entry should be removed when the cache is full.

* **EvictionAlgo implemention**：
	* **LRUCacheLineEvictionAlgo** is one of  EvictionAlgo implementions. It always evicetion the key of cache entry that least recently used.
	* **MRUCacheLineEvictionAlgo** is one of  EvictionAlgo implementions. It always evicetion the key of cache entry that most recently used.

* **Tips**:
	* Cause the eviction is a relatively performance consuming operation, so you may could to evict multi cache entries for every time if the cache set is full need to do the eviction operation. 
	
**IndexEngine**

Defines a component that can cache query conditions and cache keys so that can support search cache entry by query conditions. It can be null  in a cache if you do not need to seatch cache entries. And there is no fixed implementation so far. May you can use it like following example:

~~~
public class UserNameIndexEngine implements IndexEngine<String, User> {

	private Map<String, Set<String>> indexCache = new HashMap<>();

	@Override
	public void onPosted(String k, User v) {
		String targetKey = v.getName();
		if (indexCache.containsKey(targetKey)) {
			indexCache.get(targetKey).add(k);
		} else {
			Set<String> set = new HashSet<>();
			set.add(k);
			indexCache.put(targetKey, set);
		}
	}

	@Override
	public Collection<String> searchKey(Index index) {
		String name = index.getIndex();
		return indexCache.get(name);
	}

}

public class User {

	private String id;

	private String name;

	private String phoneNo;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phoneNo=" + phoneNo + "]";
	}

}
~~~

**Use case**

~~~
public class Example {

	public static void main(String[] args) {
		CacheBuilder<String, User> cacheBuilder = new CacheBuilder<String, User>();
		cacheBuilder.setIndexEngine(new UserNameIndexEngine());
		Cache<String, User> cache = cacheBuilder.build();
		for (int i = 0; i < 12; i++) {
			User user = new User();
			user.setId(i + "");
			user.setName("l");
			cache.set(i + "", user);
		}

		Collection<User> users = cache.search(() -> "l");
		for (User u : users) {
			System.out.println(u);
		}
	}

}
~~~

**Output**

~~~
User [id=0, name=l, phoneNo=null]
User [id=11, name=l, phoneNo=null]
User [id=1, name=l, phoneNo=null]
User [id=2, name=l, phoneNo=null]
User [id=3, name=l, phoneNo=null]
User [id=4, name=l, phoneNo=null]
User [id=5, name=l, phoneNo=null]
User [id=6, name=l, phoneNo=null]
User [id=7, name=l, phoneNo=null]
User [id=10, name=l, phoneNo=null]
~~~

* Tips:
  
  You could even join Solr/Lucene etc. full text search framework as a index egine

**SetFinder**

Defines that can get the cache set index through a provided key and also could determine to which cache set should store the coming cache entry through the cache entry key.

* **SetFinder implementions**:
	* **BalanceSetFinder** is one of set finder implemention that could let cache entries stored into each cache set evenly.
	* **HashCodeKeySetFinder** is one of set finder implemention that based on the hash code of provided cache entry key to determine which cache set should store the coming cache entry.
	
* **Tips**:
	* if you want to build super large cache system support million grade, you may could use bloom filter and binary tree technology to build a large set finder.


