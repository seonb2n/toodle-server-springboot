package com.example.toodle_server_springboot.util;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.stereotype.Component;

/**
 * 캐시 이벤트 로깅을 위한 Logger 개발시에만 작동한다.
 */
//@Profile(value = "local")
@Slf4j
@Component
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    /**
     * 캐시 이벤트 발생시 로깅
     *
     * @param cacheEvent 캐시 이벤트
     */
    public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        log.info("cache event ::: key: {}", cacheEvent.getKey());
    }

}
