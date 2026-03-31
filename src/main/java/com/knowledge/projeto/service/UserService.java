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
		
		if(dto.email() != null && repository.existsByEmailAndIdNot(dto.email(), id)) {
			throw new BusinessException("Este E-mail já está sendo usado por outro usuário.");
		}
		
		updateData(userEntity, dto);
		
		return toResponse(repository.save(userEntity));
	}
	
	private void updateData(User userEntity, UserUpdateDTO dto) {
		if(dto.username() != null) userEntity.setUsername(dto.username());
		if(dto.email() != null) userEntity.setEmail(dto.email());
		if(dto.password() != null) userEntity.setPassword(dto.password());
		if(dto.phone() != null) userEntity.setPhone(dto.phone());
	}
	
	public void delete(Long id) {
		User userEntity = repository.findByIdAndActiveTrue(id)
								    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
		userEntity.deactivate();
		repository.save(userEntity);
	}

	private UserResponseDTO toResponse(User user) {
		return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getPhone(), user.getCpf());
	}
}
