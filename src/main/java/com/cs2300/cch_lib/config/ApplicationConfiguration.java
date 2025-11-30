package com.cs2300.cch_lib.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("https://cch-lib-frontend.vercel.app");
        config.addAllowedOrigin("http://localhost:8080");

        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
