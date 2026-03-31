package com.knowledge.projeto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowledge.projeto.DTO.LoginRequestDTO;
import com.knowledge.projeto.DTO.ResponseAuthDTO;
import com.knowledge.projeto.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService service;
	
	@PostMapping("/login")
	public ResponseEntity<ResponseAuthDTO> login(@RequestBody LoginRequestDTO dto) {
			return ResponseEntity.ok(service.login(dto));
		}
}
