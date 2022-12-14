package com.lawencon.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EntityScan(basePackages = "com.lawencon")
@ComponentScan(basePackages = "com.lawencon")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class App extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
}