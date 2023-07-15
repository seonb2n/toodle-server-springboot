package com.example.toodle_server_springboot.config;

import com.example.toodle_server_springboot.util.CacheEventLogger;
import java.time.Duration;
import java.util.List;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class EhCacheConfig {

    @Autowired
    CacheEventLogger cacheEventLogger;

    @Bean
    public CacheManager ehcacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

//        MutableConfiguration<String, List> configuration =
//            new MutableConfiguration<String, List>()
//                .setTypes(String.class, List.class)
//                .setStoreByValue(false)
//                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
//
//        Cache<String, List> cache = cacheManager.createCache("projectCache", configuration);
        CacheConfigurationBuilder<String, List> configurationBuilder = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class, List.class,
                ResourcePoolsBuilder.heap(1000)
                    .offheap(25, MemoryUnit.MB))
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(3)));

        CacheEventListenerConfigurationBuilder asynchronousListener = CacheEventListenerConfigurationBuilder
            .newEventListenerConfiguration(cacheEventLogger, EventType.CREATED, EventType.EXPIRED)
            .unordered().asynchronous();

        cacheManager.createCache("projectCache", Eh107Configuration.fromEhcacheCacheConfiguration(
            configurationBuilder.withService(asynchronousListener)));

        return cacheManager;
    }
}
