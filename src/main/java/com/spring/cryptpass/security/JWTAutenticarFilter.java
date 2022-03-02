package com.spring.cryptpass.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.cryptpass.data.DetalheUsuarioData;
import com.spring.cryptpass.model.UsuarioModel;

public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter{

	public static final int TOKEN_EXPIRACAO = 600_000;
	public static final String TOKEN_SENHA = "29f0605b-2cf8-45fd-8139-968b2df8d943"; //nao poderia esta aqui 
	
	private final AuthenticationManager authenticationManager;

	public JWTAutenticarFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	 @Override
public Authentication attemptAuthentication(HttpServletRequest request, 
											HttpServletResponse response)
															throws AuthenticationException {
try {	
		//receber o json no nosso formato de model
		UsuarioModel usuario = new ObjectMapper()
				.readValue(request.getInputStream(), UsuarioModel.class);
		
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				usuario.getLogin(),
				usuario.getPassword(),
				new ArrayList<>()	//lista de permissoes do usuario 
				));
} catch (IOException e){
		throw new RuntimeException("Falha ao autenticar usuario", e);
}	

}
	 //caso aconteca um sucesso na autenticao, o que devo fazer?
	 //R: GERAR o TOKEN JWT!!!!
@Override
protected void successfulAuthentication(HttpServletRequest request,
										HttpServletResponse response, 
										FilterChain chain, 
										Authentication authResult) throws IOException, ServletException {
	
	DetalheUsuarioData usuarioData = (DetalheUsuarioData)authResult.getPrincipal();	
	
	String token = JWT.create().
			withSubject(usuarioData.getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
			.sign(Algorithm.HMAC512(TOKEN_SENHA));
	
	response.getWriter().write(token); //registrar o token pro corpo da pagina
	response.getWriter().flush();
	}	 

	 
	
}
