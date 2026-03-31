package com.knowledge.projeto.infra.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.knowledge.projeto.entity.User;
import com.knowledge.projeto.repository.UserRepository;

@Configuration
@Profile("test")
public class SetupConfig {


	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;

	public SetupConfig(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

    @Bean
    CommandLineRunner initDatabase() {
		return args -> {
			User user = User.builder().username("Lucas")
									  .email("lucas@email.com")
									  .password(passwordEncoder.encode("123456"))
									  .phone("9999-9999")
									  .cpf("222.222.222-22").build();
			
			repository.saveAll(Arrays.asList(user));
			System.out.println("Dados inicializados com sucesso!");
		};
	}
}
