package com.bdproject.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {

	private static final String ADDRESS = "http://localhost:3000";

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/division").allowedOrigins(ADDRESS);
				registry.addMapping("/division/query").allowedOrigins(ADDRESS);
				registry.addMapping("/militarychief").allowedOrigins(ADDRESS);
				registry.addMapping("/militarychief/query").allowedOrigins(ADDRESS);
				registry.addMapping("/militarygroup").allowedOrigins(ADDRESS);
				registry.addMapping("/militarygroup/query").allowedOrigins(ADDRESS);
				registry.addMapping("/politicalleader").allowedOrigins(ADDRESS);
				registry.addMapping("/politicalleader/query").allowedOrigins(ADDRESS);
				registry.addMapping("/warconflict").allowedOrigins(ADDRESS);
				registry.addMapping("/warconflict/query").allowedOrigins(ADDRESS);
				registry.addMapping("/supplies").allowedOrigins(ADDRESS);
				registry.addMapping("/supplies/query").allowedOrigins(ADDRESS);
			}
		};
	}

}
