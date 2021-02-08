package br.com.herculano.livararia_api_rest.controller.request.usuario;

import lombok.Data;

@Data
public class UsuarioConsultaRequest {

	private String nome;
	
	private String email;
	
	private String tipoUsuario;
	
	private String nomePerfil;
	
}
