package com.example.toodle_server_springboot.config;

import com.example.toodle_server_springboot.domain.project.Project;
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
        MutableConfiguration<String, Project> configuration =
            new MutableConfiguration<String, Project>()
                .setTypes(String.class, Project.class)
                .setStoreByValue(false)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        Cache<String, Project> cache = cacheManager.createCache("projectCache", configuration);
        return cacheManager;
    }

}
