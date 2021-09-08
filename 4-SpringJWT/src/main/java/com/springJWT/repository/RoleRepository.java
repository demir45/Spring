package com.springJWT.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springJWT.model.ERoller;
import com.springJWT.model.KisiRole;

@Repository
public interface RoleRepository extends JpaRepository<KisiRole, Integer>{
	
	Optional<KisiRole> findByName(ERoller name);
	
	

}
