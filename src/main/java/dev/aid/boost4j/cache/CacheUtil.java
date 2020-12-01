package dev.aid.boost4j.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * cache封装分桶
 * todo 用guava cacheBuilder重写
 * https://www.cnblogs.com/rickiyang/p/11074159.html
 *
 * @author 04637@163.com
 * @date 2020/11/27
 */
public class CacheUtil {

    private static final CacheManager cacheManager = new ConcurrentMapCacheManager();

    private static final String DEFAULT_BUCKET = "_DEFAULT_CACHE_";


    /**
     * 分桶存放缓存
     *
     * @param key    键
     * @param val    值
     * @param bucket 桶名
     */
    public static void put(String key, Object val, String bucket) {
        org.springframework.cache.Cache cache = getBucket(bucket);
        cache.put(key, val);
    }

    /**
     * 缓存至默认位置
     */
    public static void put(String key, Object val) {
        put(key, val, DEFAULT_BUCKET);
    }


    /**
     * 从桶中拿取缓存
     *
     * @param bucket 桶
     * @param type   类型
     */
    public static <T> T get(String key, String bucket, Class<T> type) {
        org.springframework.cache.Cache cache = getBucket(bucket);
        return cache.get(key, type);
    }

    public static <T> T get(String key, Class<T> type) {
        org.springframework.cache.Cache cache = getBucket(DEFAULT_BUCKET);
        return cache.get(key, type);
    }

    public static Object get(String key, String bucket) {
        org.springframework.cache.Cache cache = getBucket(bucket);
        return cache.get(key, Object.class);
    }

    public static Object get(String key) {
        org.springframework.cache.Cache cache = getBucket(DEFAULT_BUCKET);
        return cache.get(key, Object.class);
    }


    public static Set<String> getKeys() {
        return getKeys(DEFAULT_BUCKET);
    }

    public static Set<String> getKeys(String bucket) {
        org.springframework.cache.Cache cache = getBucket(bucket);
        Object store = cache.getNativeCache();
        assert store instanceof ConcurrentMap;
        ConcurrentMap<String, Object> storeMap = (ConcurrentMap<String, Object>) store;
        return storeMap.keySet();
    }

    public static void clear(String bucket) {
        org.springframework.cache.Cache cache = getBucket(bucket);
        cache.clear();
    }

    public static void clear() {
        org.springframework.cache.Cache cache = getBucket(DEFAULT_BUCKET);
        cache.clear();
    }


    @NonNull
    private static org.springframework.cache.Cache getBucket(String bucket) {
        if (StringUtils.isEmpty(bucket)) {
            bucket = DEFAULT_BUCKET;
        }
        return Objects.requireNonNull(cacheManager.getCache(bucket));
    }
}
