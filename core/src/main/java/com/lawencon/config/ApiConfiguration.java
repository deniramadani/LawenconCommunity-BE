package com.lawencon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfiguration implements WebMvcConfigurer {

	@Autowired
	private ApiRequestInterceptor apiRequestInterceptor;
	
	@Autowired
	private String[] allowedOrigins;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiRequestInterceptor);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins(allowedOrigins)
					.allowedMethods(
						HttpMethod.GET.name(),
						HttpMethod.POST.name(), 
						HttpMethod.PUT.name(), 
						HttpMethod.DELETE.name(),
						HttpMethod.PATCH.name());
			}
		};
	}

}
