package com.rzx.test.springmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@CacheConfig(cacheNames = "baseCache")//引用的缓存策略
@Slf4j
public class TestEhcacheServer {

    @Cacheable
    public Map<String,Object> testEhcache(){

        Map<String,Object> map  = new HashMap<>();
        map.put("1","张三");
        log.info("走数据库获取数据");
        return map;
    }
}
