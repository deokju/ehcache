package com.ehcache.demo;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyInterceptor extends HandlerInterceptorAdapter {
	private final CacheRepository sessionCacheRepository;
	public MyInterceptor(CacheRepository sessionCacheRepository) {
		this.sessionCacheRepository = sessionCacheRepository;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(false);

		// session이 없으면 그냥 통과
		if(hasEmptySession(session)){
			return true;
		}
		Member member = (Member)session.getAttribute("MEMBER");

		//세션은 존재하는데 유저 정보가 없으면 세션을 제거한다.
		if(member == null) {
			session.invalidate();
		}else{
			Member m =sessionCacheRepository.getCache(member.getPhoneNumber());

			//세션에 있는 유저 정보가 캐쉬에 있는 유저정보와 다른경우
			//세션의 값이 잘모되었다고 간주하고 세션을 제거한다.
			if(m == null) {
				session.invalidate();
			}
		}
		return true;
	}

	private boolean hasEmptySession(HttpSession session) {
		return session == null;
	}

}