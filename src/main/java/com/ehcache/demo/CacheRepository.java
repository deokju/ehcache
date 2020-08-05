package com.ehcache.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CacheRepository {
    void save(String phoneNumber, Member member);
    List<Member> getCacheList();
    void remove(String phoneNumber);
    Member getCache(String phoneNumber);
}
