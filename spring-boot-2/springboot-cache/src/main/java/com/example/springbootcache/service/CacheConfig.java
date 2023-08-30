package com.example.springbootcache.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {


    public static final String BIC_CODE = "BIC_CODE";
    public static final String BEN_ID = "BEN_ID";
    public static final String BANK_ID = "BANK_ID";

    @Primary
    @Bean
    public CacheManager bankCacheManager() {
        log.info("Init Cache 1");
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(Arrays.asList(BIC_CODE , BEN_ID));
        cacheManager.setCaffeine(Caffeine.newBuilder()
                //Set a fixed time to expire after the last write or access
                .expireAfterWrite(10, TimeUnit.SECONDS)
                //Initial cache space size
                .initialCapacity(6000)
                //Maximum number of cache entries
                .maximumSize(500000));
        return cacheManager;
    }

    @Bean
    public CacheManager alternateCacheManager() {
        log.info("Init Cache 2");
        return new ConcurrentMapCacheManager(BANK_ID);
    }

}
