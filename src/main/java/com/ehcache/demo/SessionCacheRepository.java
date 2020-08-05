package com.ehcache.demo;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sessionCacheRepository")
public class SessionCacheRepository implements CacheRepository{
    //ehcache.xml에 있는 캐쉬명이랑 동일해야 합니다.
    //다르면 안됩니다.
    private final String EHCACHE_NAME = "events";

    @Autowired
    private CacheManager cacheManager;

    //캐쉬 저장
    @Override
    public void save(String phoneNumber, Member member) {
        cacheManager.getCache(EHCACHE_NAME).put(phoneNumber, member);
    }

    //캐쉬 가져오기
    @Override
    public Member getCache(String phoneNumber) {
        return cacheManager.getCache(EHCACHE_NAME).get(phoneNumber, Member.class);
    }

    //캐쉬에 있는 내용을 리스트로 해서 가져온다..
    @Override
    public List<Member> getCacheList() {
        List<Member> list = new ArrayList<>();
        Cache cache = cacheManager.getCache(EHCACHE_NAME);
        Ehcache cache2 = (Ehcache)cache.getNativeCache();
        for (Object key: cache2.getKeys()) {
            Element element = cache2.get(key);
            Member m = (Member)element.getObjectValue();
            list.add(m);
        }
        return list;
    }

    //캐쉬에서 제거
    @Override
    public void remove(String phoneNumber) {
        Cache cache =  cacheManager.getCache(EHCACHE_NAME);
        cache.evictIfPresent(phoneNumber);
    }


}
