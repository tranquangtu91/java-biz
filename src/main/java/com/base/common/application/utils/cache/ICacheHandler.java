package com.base.common.application.utils.cache;

import java.util.List;

import com.base.common.application.utils.Closure;

public interface ICacheHandler {
    
    /**
     * 
     * @param key
     * @param expire đơn vị giây
     * @param closure
     * @return
     */
    Object memoize(String key, Long expire, Closure closure);

    Object memoize(String key, MemoizeOption option, Closure closure);

    Object memoizeHash(String key, Long expire, Closure closure);

    Object memoizeHash(String key, MemoizeOption option, Closure closure);

    Object memoizeHashField(String key, String field, Long expire, Closure closure);

    // HSET/HGET a value on a Redis hash at key.field
    // if expire is not null it will be the expire for the whole hash, not this
    // value
    // and will only be set if there isn't already a TTL on the hash
    Object memoizeHashField(String key, String field, MemoizeOption option, Closure closure);

    Object memoizeScore(String key, String member, Long expire, Closure closure);

    // set/get a 'double' score within a sorted set
    // if expire is not null it will be the expire for the whole zset, not this
    // value
    // and will only be set if there isn't already a TTL on the zset
    Object memoizeScore(String key, String member, MemoizeOption option, Closure closure);

    List<Object> memoizeDomainList(Class<?> domainClass, String key, Long expire, Closure closure);

    List<Object> memoizeDomainList(Class<?> domainClass, String key, MemoizeOption option, Closure closure);

    List<Long> memoizeDomainIdList(Class<?> domainClass, String key, Long expire, Closure closure);

    // used when we just want the List <Object>of Ids back rather than hydrated
    // objects
    List<Long> memoizeDomainIdList(Class<?> domainClass, String key, MemoizeOption option, Closure closure);

    List<Long> getIdListFor(String key);

    void saveIdListTo(String key, List<Object> domainList, Long expire);

    List<Object> hydrateDomainObjectsFrom(Class<?> domainClass, List<Long> idList);

    Object memoizeDomainObject(Class<?> domainClass, String key, Long expire, Closure closure);

    // closure can return either a domain object or a Long id of a domain object
    // it will be persisted into redis as the Long
    Object memoizeDomainObject(Class<?> domainClass, String key, MemoizeOption option, Closure closure);

    Long persistDomainId(Long domainId, String key, Long expire);

    Object memoizeObject(Class<?> clazz, String key, Long expire, Closure closure);

    Object memoizeObject(Class<?> clazz, String key, MemoizeOption option, Closure closure);

    /**
     * deletes all keys matching a pattern (see redis "keys" documentation for
     * more);
     * OK for low traffic methods, but expensive compared to other redis commands
     * perf test before relying on this rather than storing your own set of keys to
     * delete
     * 
     * @param keyPattern
     */
    void deleteKeysWithPattern(String keyPattern);

    Object memoizeList(String key, Long expire, Closure closure);

    Object memoizeList(String key, MemoizeOption option, Closure closure);

    Object memoizeSet(String key, Long expire, Closure closure);

    Object memoizeSet(String key, MemoizeOption option, Closure closure);

    List<String> keys();
}
