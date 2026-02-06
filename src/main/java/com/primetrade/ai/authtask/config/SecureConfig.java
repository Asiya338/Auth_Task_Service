package com.primetrade.ai.authtask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.primetrade.ai.authtask.exception.CustomAccessDeniedHandler;
import com.primetrade.ai.authtask.exception.CustomAuthenticationEntryPoint;
import com.primetrade.ai.authtask.security.JWTAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecureConfig {

	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).formLogin(
				form -> form.disable()).httpBasic(
						basic -> basic.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**",
								"/actuator/health", "/actuator/info").permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint)
						.accessDeniedHandler(customAccessDeniedHandler));

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}