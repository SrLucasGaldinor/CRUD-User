package com.knowledge.projeto.infra.security;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint unauthorizedHandler;
	private final CustomUserDetailsService userDetailsService;
	private final SecurityFilter securityFilter;

	public SecurityConfig(CustomUserDetailsService userDetailsService, SecurityFilter securityFilter, JwtAuthenticationEntryPoint unauthorizedHandler) {
		this.userDetailsService = userDetailsService;
		this.securityFilter = securityFilter;
		this.unauthorizedHandler = unauthorizedHandler;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(exception -> exception
					.authenticationEntryPoint(this.unauthorizedHandler))
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
					.requestMatchers(PathRequest.toH2Console()).permitAll()
					.anyRequest().authenticated()
					)
			.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
			.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
}
