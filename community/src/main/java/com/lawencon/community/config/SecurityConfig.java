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

	@Bean
	public String[] getAllowedOrigins() {
		final String[] allowedOrigins = new String[] {"http://localhost:4200"};
		return allowedOrigins;
	}
	@Bean(name = "webIgnoring")
	public List<RequestMatcher> matchers() {
		final List<RequestMatcher> matchers = new ArrayList<>();
		matchers.add(new AntPathRequestMatcher("/swagger-ui/**", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/v3/**", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/files/**", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/login/**", HttpMethod.POST.name()));
		matchers.add(new AntPathRequestMatcher("/register/member/**", HttpMethod.POST.name()));
		matchers.add(new AntPathRequestMatcher("/verification-code/**", HttpMethod.POST.name()));
		
		return matchers;
	}

}
