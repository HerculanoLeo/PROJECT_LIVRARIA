package br.com.herculano.livrariaREST.controller.request;

import lombok.Data;

@Data
public class UsuarioRequest {

	private String nome;
	
	private String email;
	
	private String senha;
	
	private String confirmeSenha;
	
}
