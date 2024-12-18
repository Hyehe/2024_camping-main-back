package com.ict.camping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // CSRF 보호 비활성화
            .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/api/camping/sites/**").permitAll() // 특정 경로 인증 없이 허용
                .anyRequest().authenticated() // 그 외의 요청은 인증 필요
            .and()
            .httpBasic(); // HTTP 기본 인증 방식 사용
        return http.build();
    }
    @Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("http://localhost:3000"); // 허용할 클라이언트 도메인
    configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
    configuration.addAllowedHeader("*"); // 모든 헤더 허용
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ws/**", "/topic/**", "/app/**").permitAll() // WebSocket 관련 요청 허용
                .anyRequest().permitAll() // 나머지 요청 허용 (테스트용)
            );
        return http.build();
    }
}