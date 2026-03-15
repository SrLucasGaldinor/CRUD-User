package com.knowledge.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.knowledge.demo.DTO.UserCreateDTO;
import com.knowledge.demo.DTO.UserResponseDTO;
import com.knowledge.demo.entity.User;
import com.knowledge.demo.exception.BusinessException;
import com.knowledge.demo.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public List<UserResponseDTO> findAll() {
		return repository.findAllByActiveTrue().stream().map(this::toResponse).collect(Collectors.toList());
	}

	public UserResponseDTO create(UserCreateDTO dto) {
		
		if(repository.existsByEmail(dto.email())) {
			throw new BusinessException("Email já cadastrado!");
		}
		
		if(repository.existsByCpf(dto.cpf())) {
			throw new BusinessException("CPF já cadastrado!");
		}
		
		User user = User.builder()
						.username(dto.username())
						.email(dto.email())
						.password(dto.password())
						.phone(dto.phone())
						.cpf(dto.cpf())
						.active(true)
						.build();
		
		return toResponse(repository.save(user));
	}

	private UserResponseDTO toResponse(User user) {
		return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getPhone(), user.getCpf());
	}
}
