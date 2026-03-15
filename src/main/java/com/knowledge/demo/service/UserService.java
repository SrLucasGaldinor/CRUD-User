package com.knowledge.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.knowledge.demo.DTO.UserCreateDTO;
import com.knowledge.demo.DTO.UserResponseDTO;
import com.knowledge.demo.DTO.UserUpdateDTO;
import com.knowledge.demo.entity.User;
import com.knowledge.demo.exception.BusinessException;
import com.knowledge.demo.exception.ResourceNotFoundException;
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
	
	public UserResponseDTO findById(Long id) {
		return repository.findByIdAndActiveTrue(id)
						 .map(this::toResponse)
						 .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
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
						.build();
		
		return toResponse(repository.save(user));
	}
	
	public UserResponseDTO update(Long id, UserUpdateDTO dto) {
		User userEntity = repository.findByIdAndActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
		
		User userUpdate = User.builder()
				.id(userEntity.getId())
				.username(dto.username() != null ? dto.username() : userEntity.getUsername())
				.email(dto.email() != null ? dto.email() : userEntity.getEmail())
				.password(dto.password() != null ? dto.password() : userEntity.getPassword())
				.phone(dto.phone() != null ? dto.phone() : userEntity.getPhone())
				.cpf(userEntity.getCpf())
				.build();
		
		return toResponse(repository.save(userUpdate));
	}

	private UserResponseDTO toResponse(User user) {
		return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getPhone(), user.getCpf());
	}
}
