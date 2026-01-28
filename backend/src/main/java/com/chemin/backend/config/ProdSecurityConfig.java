package com.chemin.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Profile("prod")
@Configuration
@EnableWebSecurity
public class ProdSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/public/**").permitAll() // 公開路徑
                        .anyRequest().authenticated() // 其他都要登入
                )
                .formLogin(Customizer.withDefaults())       // 預設表單登入
                .httpBasic(Customizer.withDefaults());      // Basic Auth

        return http.build();
    }
}

