package com.knowledge.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {

	private String name;
	private String email;
	private String phone;
	private String cpf;
}
