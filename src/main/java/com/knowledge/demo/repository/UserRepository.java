package com.knowledge.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.knowledge.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	List<User> findAllByActiveTrue();
	
	Optional<User> findByIdAndActiveTrue(Long id);
	
	boolean existsByEmail(String email);

	boolean existsByEmailAndIdNot(String email, Long id);
	
	boolean existsByCpf(String cpf);
	
}
