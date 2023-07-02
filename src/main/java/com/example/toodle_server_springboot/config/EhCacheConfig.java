package com.example.toodle_server_springboot.config;

import java.util.List;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class EhCacheConfig {

    @Bean
    public CacheManager ehcacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        MutableConfiguration<String, List> configuration =
            new MutableConfiguration<String, List>()
                .setTypes(String.class, List.class)
                .setStoreByValue(false)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        Cache<String, List> cache = cacheManager.createCache("projectCache", configuration);

        // CacheEntryListenerConfiguration 등록
//        Factory<CacheEntryListener<? super String, ? super List>> factory = () -> (CacheEntryListener<? super String, ? super List>) cacheEventLogger;
//        CacheEntryListenerConfiguration<String, List> listenerConfiguration =
//            new MutableCacheEntryListenerConfiguration<>(
//                factory,
//                null,
//                false,
//                true
//            );
//        cache.registerCacheEntryListener(listenerConfiguration);

        return cacheManager;
    }
}
