package br.com.herculano.livararia_api_rest.controller.request;

import lombok.Data;

@Data
public class TrocaSenhaComCodigoRequest {

	private String email;
	
	private String codigo;
	
	private String novaSenha;
	
	private String confirmaSenha;
	
}
