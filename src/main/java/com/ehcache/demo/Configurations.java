package com.ehcache.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration   //스프링부트 웹 설정
public class Configurations implements WebMvcConfigurer {

    @Autowired
    private CacheRepository sessionCacheRepository;

	@Override  //인터셉터 등록
	public void addInterceptors(InterceptorRegistry registry) {
        List<String> URL_PATTERNS = Arrays.asList("/*");
		registry.addInterceptor(new MyInterceptor(sessionCacheRepository))  //만들어준 인터셉터
			.addPathPatterns(URL_PATTERNS)   //이런식으로 배열로도 가능하고,
			.excludePathPatterns("/resources/**")   //1개1개씩 추가도 가능하다.
			.excludePathPatterns("/create/session");
	}

}