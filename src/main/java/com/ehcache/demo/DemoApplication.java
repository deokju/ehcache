package com.ehcache.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@SpringBootApplication
@EnableCaching
@Controller
public class DemoApplication {
    private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private CacheRepository sessionCacheRepository;

    @GetMapping("/")
    public String setCache(){
        return "main";
    }

    @GetMapping("/create/session")
    @ResponseBody
    public String createSession(@RequestParam String uname, @RequestParam String phoneNumber, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member member = new Member(session.getId(), uname, phoneNumber);
        session.setAttribute("MEMBER", member);
        sessionCacheRepository.save(phoneNumber, member);
        return "ok";
    }

    @GetMapping("/list")
    public String listSession(ModelMap model){
        List<Member> list = sessionCacheRepository.getCacheList();
        model.addAttribute("loginUsers", list);
        return "list";
    }

    @GetMapping("/kill/session")
    @ResponseBody
    public String killSession(@RequestParam String phoneNumber){
        sessionCacheRepository.remove(phoneNumber);
        return "list";
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.run(args);
    }

}
