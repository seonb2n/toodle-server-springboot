package com.example.toodle_server_springboot.config;

import com.example.toodle_server_springboot.util.CacheEventLogger;
import java.util.List;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import lombok.RequiredArgsConstructor;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.event.EventType;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
@RequiredArgsConstructor
public class EhCacheConfig {

    private final CacheEventLogger cacheEventLogger;

    @Bean
    public CacheManager ehcacheManager() {
        CacheEventListenerConfigurationBuilder cacheEventListenerConfiguration = CacheEventListenerConfigurationBuilder
            .newEventListenerConfiguration(cacheEventLogger, EventType.CREATED, EventType.UPDATED)
            .unordered().asynchronous();

        //        MutableConfiguration<String, List> configuration =
//            new MutableConfiguration<String, List>()
//                .setTypes(String.class, List.class)
//                .setStoreByValue(false)
//                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE))
//            ;

//        Cache<String, List> cache = manager.getCache("projectCache", String.class, List.class);

        // CacheManager의 Configuration 가져오기

        var manager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("projectCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, List.class,
                        ResourcePoolsBuilder.heap(10))
                    .add(cacheEventListenerConfiguration)
            ).build(true);
        var cahce = manager.getCache("projectCache", String.class, List.class);
        CachingProvider provider = Caching.getCachingProvider();
//        javax.cache.CacheManager cacheManager = provider.getCacheManager();
//        javax.cache.CacheManager cacheManager = new Cache
        return manager;
    }


}
