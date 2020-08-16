package br.com.herculano.livrariaREST.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioTrocaSenhaRequest {

	@NotBlank
	private String email;
	
	private String novaSenha;
	
	private String senhaAntiga;
	
	private String confirmaSenha;
	
}
