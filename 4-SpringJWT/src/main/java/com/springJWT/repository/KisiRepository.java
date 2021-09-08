package com.springJWT.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springJWT.model.Kisi;

@Repository
public interface KisiRepository extends JpaRepository<Kisi, Long>{

	Boolean existsByUsername(String name);
	Boolean existsByEmail(String email);
	
	Optional<Kisi> findByUsername(String username);
	
	
	
}
