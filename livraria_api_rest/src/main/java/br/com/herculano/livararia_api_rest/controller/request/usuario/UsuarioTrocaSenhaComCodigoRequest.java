package br.com.herculano.livararia_api_rest.controller.request.usuario;

import lombok.Data;

@Data
public class UsuarioTrocaSenhaComCodigoRequest {

	private String email;
	
	private String code;
	
	private String senha;
	
	private String confirmaSenha;
	
}
