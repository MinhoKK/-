package com.ssafy.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

	private static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,DELETE,TRACE,OPTIONS,PATCH,PUT";
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry
	        .addMapping("/**")
	        .allowedOrigins("http://localhost:5173",
	        		"http://localhost:8080")
	        .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
	        .allowedHeaders("*")
	        .allowCredentials(true)
	        .exposedHeaders(AUTHORIZATION, LOCATION, SET_COOKIE);
	        // .maxAge(1800);
	}
}
