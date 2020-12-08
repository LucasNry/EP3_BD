package com.bdproject.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/division").allowedOrigins("http://localhost:3000");
				registry.addMapping("/militarychief").allowedOrigins("http://localhost:3000");
				registry.addMapping("/militarygroup").allowedOrigins("http://localhost:3000");
				registry.addMapping("/politicalleader").allowedOrigins("http://localhost:3000");
				registry.addMapping("/warconflict").allowedOrigins("http://localhost:3000");
			}
		};
	}

}
