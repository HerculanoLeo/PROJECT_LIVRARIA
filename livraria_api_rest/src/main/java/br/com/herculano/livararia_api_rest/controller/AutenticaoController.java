package br.com.herculano.livararia_api_rest.controller;

import static br.com.herculano.livararia_api_rest.auth.token.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.herculano.livararia_api_rest.auth.token.AuthToken;
import br.com.herculano.livararia_api_rest.configuration.security.TokenProvider;
import br.com.herculano.livararia_api_rest.controller.request.LoginRequest;
import br.com.herculano.livararia_api_rest.controller.response.AutenticacaoResponse;
import br.com.herculano.livararia_api_rest.entity.Usuario;

@RestController
@RequestMapping("/auth")
public class AutenticaoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginRequest request) {
		try {
			final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			final Date expireToken = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS);
			final String token = jwtTokenUtil.generateToken(authentication, expireToken);
			
			Usuario usuario = (Usuario) authentication.getPrincipal();
			
			Object principal = new AutenticacaoResponse(usuario);
				
			return ResponseEntity.ok(new AuthToken(token, expireToken, principal));
		} catch (AuthenticationException e) {
			throw e;
		}
	}
}
