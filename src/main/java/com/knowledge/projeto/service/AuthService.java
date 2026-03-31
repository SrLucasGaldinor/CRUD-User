package com.knowledge.projeto.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.knowledge.projeto.DTO.LoginRequestDTO;
import com.knowledge.projeto.DTO.ResponseAuthDTO;
import com.knowledge.projeto.entity.User;
import com.knowledge.projeto.exception.InvalidPasswordException;
import com.knowledge.projeto.exception.ResourceNotFoundException;
import com.knowledge.projeto.infra.security.TokenService;
import com.knowledge.projeto.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;

	public ResponseAuthDTO login(LoginRequestDTO dto) {
		User user = userRepository.findByEmail(dto.email()).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
		
		if(passwordEncoder.matches(dto.password(), user.getPassword())) {
			String token = tokenService.generateToken(user);
			return new ResponseAuthDTO(token, user.getUsername());
		} 
		throw new InvalidPasswordException("Invalid password!");
	}
}
