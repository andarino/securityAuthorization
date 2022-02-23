package com.spring.cryptpass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.cryptpass.model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
	
	//optional é para evitar null pointer exception
	public Optional<UsuarioModel> findByLogin(String login); 
	//public UsuarioModel save(UsuarioModel usuario);
	
}