package br.com.herculano.livararia_api_rest.controller.request.usuario;

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
