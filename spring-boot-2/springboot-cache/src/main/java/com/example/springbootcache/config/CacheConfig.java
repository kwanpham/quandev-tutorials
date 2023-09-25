package com.example.springbootcache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
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
    public CacheManager caffeineCacheManager() {

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(Arrays.asList(BIC_CODE, BEN_ID));
        cacheManager.setCaffeine(Caffeine.newBuilder()
                //Set a fixed time to expire after the last write or access
                .expireAfterWrite(10, TimeUnit.SECONDS)
                //Initial cache space size
                .initialCapacity(6000)
                //Maximum number of cache entries
                .maximumSize(500000));
        log.info("Init done caffeineCacheManager");
        return cacheManager;
    }

    @Bean
    public CacheManager concurrentMapCacheManager() {
        log.info("Init done concurrentMapCacheManager");
        return new ConcurrentMapCacheManager(BANK_ID);
    }

    @Bean
    public CacheManager hazelcastCacheManager() {

        Config config = new Config();
//        IntegrityCheckerConfig integrityCheckerConfig = new IntegrityCheckerConfig();
//        integrityCheckerConfig.setEnabled(false);
//        config.setIntegrityCheckerConfig(integrityCheckerConfig);

        JoinConfig joinConfig = config.getNetworkConfig().getJoin();

        // disable/enable multicast config for demo
        joinConfig.getMulticastConfig()
                .setEnabled(false);

        // enable tcp/ip config for demo
        joinConfig.getTcpIpConfig()
                .setMembers(Arrays.asList("localhost:5701" , "localhost:5702"))
                .setEnabled(true);

        MapConfig usersMapConfig = new MapConfig()
                .setName(BANK_ID)
                .setTimeToLiveSeconds(30);
    //            .setEvictionPolicy(EvictionPolicy.LFU);

        config.addMapConfig(usersMapConfig);

        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);

        log.info("Init done hazelcastCacheManager");
        return new HazelcastCacheManager(hazelcastInstance);

    }


}
