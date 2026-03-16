package com.knowledge.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name = "tb_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private String phone;
	
	@Column(unique = true)
	private String cpf;
	
	@Builder.Default
	private boolean active = true;
	
	public User(String username, String email, String password, String phone, String cpf) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.cpf = cpf;
	}
		
	public void activate() {
		this.active = true;
	}
	
	public void deactivate() {
		this.active = false;
	}
}
