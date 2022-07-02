package com.example.wt2;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.wt2.authcauthz.accRealm;

@SpringBootApplication
public class ToDoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
	}
	
	@Bean
	public Realm realm() {
		return new accRealm();
	}
		/* https://shiro.apache.org/spring-framework.html */
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
	    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

	    // logged in users with the 'admin' role
	    chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

	    // logged in users with the 'document:read' permission
	    chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

	    // all other paths require a logged in user
	    chainDefinition.addPathDefinition("/**", "authc");
	    chainDefinition.addPathDefinition("/api**", "authc");
	    return chainDefinition;
	}
	        // enabling caching
	        @Bean
	        protected CacheManager cacheManager() {
	            return new MemoryConstrainedCacheManager();
	        }
	
}
