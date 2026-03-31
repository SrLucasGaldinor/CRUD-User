package com.knowledge.demo.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.knowledge.demo.entity.User;
import com.knowledge.demo.repository.UserRepository;

@Configuration
@Profile("test")
public class SetupConfig {


	private final UserRepository repository;

	public SetupConfig(UserRepository repository) {
		this.repository = repository;
	}

    @Bean
    CommandLineRunner initDatabase() {
		return args -> {
			User user1 = User.builder().username("Erick").email("erick@email.com").password("123456").phone("9999-9999")
					.cpf("111.111.111-11").build();

			User user2 = User.builder().username("Lucas").email("lucas@email.com").password("123456").phone("9999-9999")
					.cpf("222.222.222-22").build();

			User user3 = User.builder().username("Gustavo").email("gustavo@email.com").password("123456")
					.phone("9999-9999").cpf("333.333.333-33").build();

			repository.saveAll(Arrays.asList(user1, user2, user3));
			
			System.out.println("Dados inicializados com sucesso!");
		};
	}
}
