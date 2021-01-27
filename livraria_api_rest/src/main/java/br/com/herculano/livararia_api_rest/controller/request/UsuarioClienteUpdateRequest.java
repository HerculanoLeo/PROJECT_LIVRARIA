package br.com.herculano.livararia_api_rest.controller.request;

import lombok.Data;

@Data
public class UsuarioClienteUpdateRequest {

	private Integer idUsuario;

	private String nome;
	
	private String email;
	
	private String documento;
	
}
