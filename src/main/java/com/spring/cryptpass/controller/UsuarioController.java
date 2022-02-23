package com.spring.cryptpass.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

import com.spring.cryptpass.model.UsuarioModel;
import com.spring.cryptpass.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;
	
	//o construtor é modificado para nao da BO por causa do 'final'
	public UsuarioController(UsuarioRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}
	
@GetMapping("/lsAll")	
public ResponseEntity<List<UsuarioModel>> listarTodos(){
	return ResponseEntity.ok(repository.findAll());
}

//RequestBody vai receber um json de entrada e vai converte-lo em objeto

@PostMapping("/salvar")
@ResponseBody
public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuario){
	usuario.setPassword(encoder.encode(usuario.getPassword())); //encriptando a senha antes salva-la
	return ResponseEntity.ok(repository.save(usuario));
}


@GetMapping("/validarSenha")
public ResponseEntity<Boolean> validarSenha(@RequestParam String login, @RequestParam String password)
{
	
	Optional<UsuarioModel> optUsuario = repository.findByLogin(login);
	
	if(optUsuario.isEmpty()) { //nao tem usuario no optional 
		 // retorna nao auto se o usuario nao for encontrado pelo login
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
	}
	
	UsuarioModel usuario = optUsuario.get(); //pega o user do ooptionsal
	boolean valid  = encoder.matches(password, usuario.getPassword()); //verifica se a senha informada é igual a salva no banco
	
	HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED; //verificando status http
	
	return ResponseEntity.status(status).body(valid);
}

/*
 * //@PostMapping("/saveUser") //@ResponseBody //public
 * ResponseEntity<UsuarioModel> saveUser(@RequestBody UsuarioModel usuario){ //
 * try { // UsuarioModel _usuario = repository.saveAndFlush(usuario); // return
 * new ResponseEntity<>(_usuario, HttpStatus.CREATED); // // }catch(Exception e)
 * { // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); //
 * } // // //}
 * 
 */
}
