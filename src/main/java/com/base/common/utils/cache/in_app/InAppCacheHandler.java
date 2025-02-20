package com.base.common.utils.cache.in_app;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import com.base.common.utils.Closure;
import com.base.common.utils.cache.ICacheHandler;
import com.base.common.utils.cache.MemoizeOption;
import com.base.common.utils.convert.object.ObjectUtils;
import com.base.common.utils.regex.RegexUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InAppCacheHandler implements ICacheHandler {
    static Map<String, StorageObject> generalStorage = new ConcurrentHashMap<>();
    static Map<String, StorageObject> domainObjectStorage = new ConcurrentHashMap<>();

    static Long cleanDataNextTime = 0l;
    static final Long CLEAR_DATA_INTERVAL_IN_MS = 60000l;

    @Override
    public Object memoize(String key, Long expire, Closure closure) {
        MemoizeOption memoizeOption = new MemoizeOption();
        memoizeOption.expire = expire;
        return memoize(key, memoizeOption, closure);
    }

    @Override
    public Object memoize(String key, MemoizeOption option, Closure closure) {
        Long expire = option.expire == null ? 86400l : option.expire;

        Long currentTime = System.currentTimeMillis();
        Object result;
        StorageObject storageObject = generalStorage.get(key);

        if (ObjectUtils.isEmpty(storageObject) || storageObject.nextTime2Expire < currentTime) {
            generalStorage.remove(key);
            cleanData();

            result = closure.handler();
            storageObject = new StorageObject();
            storageObject.expireTime = expire * 1000l;
            storageObject.nextTime2Expire = currentTime + storageObject.expireTime;
            storageObject.value = result;
            generalStorage.put(key, storageObject);
        } else {
            result = generalStorage.get(key).value;
        }

        return result;
    }

    @Override
    public Object memoizeHash(String key, Long expire, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeHash'");
    }

    @Override
    public Object memoizeHash(String key, MemoizeOption option, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeHash'");
    }

    @Override
    public Object memoizeHashField(String key, String field, Long expire, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeHashField'");
    }

    @Override
    public Object memoizeHashField(String key, String field, MemoizeOption option, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeHashField'");
    }

    @Override
    public Object memoizeScore(String key, String member, Long expire, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeScore'");
    }

    @Override
    public Object memoizeScore(String key, String member, MemoizeOption option, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeScore'");
    }

    @Override
    public List<Object> memoizeDomainList(Class<?> domainClass, String key, Long expire, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeDomainList'");
    }

    @Override
    public List<Object> memoizeDomainList(Class<?> domainClass, String key, MemoizeOption option, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeDomainList'");
    }

    @Override
    public List<Long> memoizeDomainIdList(Class<?> domainClass, String key, Long expire, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeDomainIdList'");
    }

    @Override
    public List<Long> memoizeDomainIdList(Class<?> domainClass, String key, MemoizeOption option, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeDomainIdList'");
    }

    @Override
    public List<Long> getIdListFor(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdListFor'");
    }

    @Override
    public void saveIdListTo(String key, List<Object> domainList, Long expire) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveIdListTo'");
    }

    @Override
    public List<Object> hydrateDomainObjectsFrom(Class<?> domainClass, List<Long> idList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hydrateDomainObjectsFrom'");
    }

    @Override
    public Object memoizeDomainObject(Class<?> domainClass, String key, Long expire, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeDomainObject'");
    }

    @Override
    public Object memoizeDomainObject(Class<?> domainClass, String key, MemoizeOption option, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeDomainObject'");
    }

    @Override
    public Long persistDomainId(Long domainId, String key, Long expire) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'persistDomainId'");
    }

    @Override
    public Object memoizeObject(Class<?> clazz, String key, Long expire, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeObject'");
    }

    @Override
    public Object memoizeObject(Class<?> clazz, String key, MemoizeOption option, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeObject'");
    }

    @Override
    public void deleteKeysWithPattern(String keyPattern) {
        Pattern pattern = RegexUtils.globToRegExp(keyPattern.toString());

        for (Iterator<Entry<String, StorageObject>> iter = generalStorage.entrySet().iterator(); iter.hasNext();) {
            Entry<String, StorageObject> entry = iter.next();
            if (pattern.matcher(entry.getKey()).matches())
                iter.remove();
        }
        for (Iterator<Entry<String, StorageObject>> iter = domainObjectStorage.entrySet().iterator(); iter.hasNext();) {
            Entry<String, StorageObject> entry = iter.next();
            if (pattern.matcher(entry.getKey()).matches())
                iter.remove();
        }
    }

    @Override
    public Object memoizeList(String key, Long expire, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeList'");
    }

    @Override
    public Object memoizeList(String key, MemoizeOption option, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeList'");
    }

    @Override
    public Object memoizeSet(String key, Long expire, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeSet'");
    }

    @Override
    public Object memoizeSet(String key, MemoizeOption option, Closure closure) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'memoizeSet'");
    }

    @Override
    public List<String> keys() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keys'");
    }

    static Long cleanData() {
        Long count = 0l;
        Long currentTime = System.currentTimeMillis();

        if (cleanDataNextTime > currentTime)
            return count;
        cleanDataNextTime = currentTime + CLEAR_DATA_INTERVAL_IN_MS;

        try {
            for (Iterator<Entry<String, StorageObject>> iter = generalStorage.entrySet().iterator(); iter.hasNext();) {
                Entry<String, StorageObject> entry = iter.next();
                if (entry.getValue().nextTime2Expire < currentTime) {
                    iter.remove();
                }
            }

            for (Iterator<Entry<String, StorageObject>> iter = domainObjectStorage.entrySet().iterator(); iter
                    .hasNext();) {
                Entry<String, StorageObject> entry = iter.next();
                if (entry.getValue().nextTime2Expire < currentTime) {
                    iter.remove();
                }
            }
        } catch (Exception ex) {
            log.error(String.format("%s: %s", ex.getClass().getName(), ex.getMessage()));
        }

        return count;
    }

}
