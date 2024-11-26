package com.ssafy.web.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.web.interceptor.LoginCheckInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthorizationConfiguration implements WebMvcConfigurer{

	private final LoginCheckInterceptor loginCheckInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> excludePatterns = new ArrayList<>(Arrays.asList("/", "/error", "/*.ico", "/user/**", "/house/**", "/facility/**"));
		
		registry.addInterceptor(loginCheckInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns(excludePatterns);
	}
	
	
}
