package com.base.common.application.utils.cache;

import java.util.HashMap;
import java.util.Map;

import com.base.common.application.utils.cache.impl.inapp.InAppCacheHandler;

public class CacheHandlerFactory {

    static Map<String, ICacheHandler> cacheHandlers = new HashMap<>();

    static InAppCacheHandler inAppCacheHandler = new InAppCacheHandler();

    public static ICacheHandler getCacheHandler(String driver) {
        ICacheHandler cacheHandler = cacheHandlers.get(driver);
        if (cacheHandler == null)
            return inAppCacheHandler;
        return cacheHandler;
    }

    public static ICacheHandler getCacheHandler() {
        return inAppCacheHandler;
    }

    public static void registerCacheHandler(String driver, ICacheHandler cacheHandler) {
        cacheHandlers.put(driver, cacheHandler);
    }
}
