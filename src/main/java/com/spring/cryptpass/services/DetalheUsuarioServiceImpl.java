package com.spring.cryptpass.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.spring.cryptpass.data.DetalheUsuarioData;
import com.spring.cryptpass.model.UsuarioModel;
import com.spring.cryptpass.repository.UsuarioRepository;

//cliente do spring
@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService{

	private final UsuarioRepository repository;
	
	public DetalheUsuarioServiceImpl(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	@Override //mmetodo que faz a consulta do usuario
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UsuarioModel> usuario = repository.findByLogin(username);
		
		if (usuario.isEmpty()) {
			throw new UsernameNotFoundException("usuario ["+username+"] nao encontrado" );
		}
			
		return new DetalheUsuarioData(usuario);
	}



}
