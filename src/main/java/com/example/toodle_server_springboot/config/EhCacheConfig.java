package com.example.toodle_server_springboot.config;

import com.example.toodle_server_springboot.domain.project.Project;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhCacheConfig {

    @Bean
    public CacheManager ehcacheManager() {

        // 최대 힙 메모리 용량 설정
        return CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("projectCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Project.class,
                    ResourcePoolsBuilder.heap(
                            100) // 최대 힙 메모리 용량을 원하는 값으로 변경 (메모리 단위는 개수, 기본 단위는 엔트리)
                        .offheap(50, MemoryUnit.MB) // 최대 오프힙 메모리 용량을 원하는 값으로 변경
                        .disk(200, MemoryUnit.MB) // 최대 디스크 메모리 용량을 원하는 값으로 변경
                )
            )
            .build(true);
//        CachingProvider provider = Caching.getCachingProvider();
//        CacheManager cacheManager = provider.getCacheManager();
//        MutableConfiguration<String, Project> configuration = new MutableConfiguration<String, Project>()
//            .setTypes(String.class, Project.class)
//            .setStoreByValue(false)
//            .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_HOUR))
//            ;
//
//        Cache<String, Project> cache = cacheManager.createCache("projectCache", configuration);
    }

}
