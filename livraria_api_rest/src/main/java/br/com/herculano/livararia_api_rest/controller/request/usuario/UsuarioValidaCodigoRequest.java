package br.com.herculano.livararia_api_rest.controller.request.usuario;

import lombok.Data;

@Data
public class UsuarioValidaCodigoRequest {

	private String email;
	
	private String code;
	
}
