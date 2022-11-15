package com.lawencon.community.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityConfig {
	
	@Bean(name="webIgnoring")
	public List<RequestMatcher> matchers(){
		final List<RequestMatcher> matchers = new ArrayList<>();
		matchers.add(new AntPathRequestMatcher("/swagger-ui/**", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/v3/**", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/login/**", HttpMethod.POST.name()));
		matchers.add(new AntPathRequestMatcher("/files/download/**", HttpMethod.GET.name()));
//		matchers.add(new AntPathRequestMatcher("/**", HttpMethod.POST.name()));
//		matchers.add(new AntPathRequestMatcher("/users/**", HttpMethod.POST.name()));
//		matchers.add(new AntPathRequestMatcher("/users/**", HttpMethod.PUT.name()));
		return matchers;
	}
	
}
