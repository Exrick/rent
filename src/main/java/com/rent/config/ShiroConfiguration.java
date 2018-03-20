package com.rent.config;

import com.rent.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Exrickx
 */
@Slf4j
@Configuration
public class ShiroConfiguration {
    
    @Autowired
    private UserService userService;
    
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager) {

        log.info("开始加载Shiro");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //anon表示不需要验证，authc表示需要验证身份才能访问

        //页面
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");

        //接口
        filterChainDefinitionMap.put("/captcha/**", "anon");
        filterChainDefinitionMap.put("/admin/login", "anon");
        filterChainDefinitionMap.put("/user/login", "anon");

        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");

        //过滤样式
        filterChainDefinitionMap.put("/**/*.js", "anon");
        filterChainDefinitionMap.put("/**/*.css", "anon");
        filterChainDefinitionMap.put("/**/*.png", "anon");
        filterChainDefinitionMap.put("/swagger/**", "anon");
        //避免一些浏览器登录不跳转直接下载favicon.ico文件
        filterChainDefinitionMap.put("/**/favicon.ico", "anon");

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setLoginUrl("/user/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * SecurityManager是Shiro架构的核心通过它来链接Realm和用户(文档中称之为Subject)
     * @return
     */
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将Realm注入到SecurityManager中
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    @Bean
    public MyRealm myShiroRealm(){
        MyRealm myShiroRealm = new MyRealm();
        return myShiroRealm;
    }

}
