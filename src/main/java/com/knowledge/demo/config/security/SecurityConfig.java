package com.knowledge.demo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;
	private final SecurityFilter securityFilter;

	public SecurityConfig(CustomUserDetailsService userDetailsService, SecurityFilter securityFilter) {
		this.userDetailsService = userDetailsService;
		this.securityFilter = securityFilter;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers(HttpMethod.POST, "/login").permitAll()
					.requestMatchers(HttpMethod.POST, "/register").permitAll()
					.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ROLE_ADMIN")
					.anyRequest().authenticated()
					)
			.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
