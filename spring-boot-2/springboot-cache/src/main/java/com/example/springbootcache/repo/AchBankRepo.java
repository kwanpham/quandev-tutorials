package com.example.springbootcache.repo;

import com.example.springbootcache.entity.AchBankEntity;
import com.example.springbootcache.config.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AchBankRepo extends JpaRepository<AchBankEntity, String> {

    @Cacheable(value = CacheConfig.BIC_CODE, key = "#benId" , unless="#result == null" , cacheManager = "caffeineCacheManager")
    @Query(value = "select ab.BIC_CODE from ACH_BANK ab where ab.BEN_ID = :benId" , nativeQuery = true)
    String findBicCodeByBenId(@Param("benId") String benId);

    @Cacheable(value = CacheConfig.BEN_ID , key = "#binId" , unless="#result == null" , cacheManager = "concurrentMapCacheManager")
    @Query(value = "select ab.BEN_ID from ACH_BANK ab inner join ACH_BANK_BIN abb on ab.BIC_CODE = abb.BIC_CODE where abb.BIN_ID = :binId" , nativeQuery = true)
    String findBenIdByBinId(@Param("binId") String binId);

    @Cacheable(value = CacheConfig.BANK_ID , key = "#bicCode" , unless="#result == null" , cacheManager = "hazelcastCacheManager")
    @Query(value = "select ab.BANK_ID from ACH_BANK ab where ab.BIC_CODE = :bicCode" , nativeQuery = true)
    String findBankIdByBicCode(@Param("bicCode") String bicCode);

}
