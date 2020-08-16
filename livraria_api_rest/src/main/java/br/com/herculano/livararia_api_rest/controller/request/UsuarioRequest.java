package br.com.herculano.livararia_api_rest.controller.request;

import lombok.Data;

@Data
public class UsuarioRequest {

	private String nome;
	
	private String email;
	
	private String senha;
	
	private String confirmeSenha;
	
}
