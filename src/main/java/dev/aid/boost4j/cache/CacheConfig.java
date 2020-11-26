package dev.aid.boost4j.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存 https://www.baeldung.com/spring-cache-tutorial
 *
 * @author 04637@163.com
 * @date 2020/11/26
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("cache");
    }

    @Autowired
    CacheManager cacheManager;

    @Bean
    public Cache cache() {
        return cacheManager.getCache("cache");
    }
}
