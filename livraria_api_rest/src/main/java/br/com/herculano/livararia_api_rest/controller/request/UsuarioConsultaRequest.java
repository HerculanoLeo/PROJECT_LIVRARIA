package br.com.herculano.livararia_api_rest.controller.request;

import lombok.Data;

@Data
public class UsuarioConsultaRequest {

	private String nome;
	
	private String email;
	
	private String tipoUsuario;
	
	private String perfil;
	
}
