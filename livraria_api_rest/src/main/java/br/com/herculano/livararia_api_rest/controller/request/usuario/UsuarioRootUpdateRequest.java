package br.com.herculano.livararia_api_rest.controller.request.usuario;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioRootUpdateRequest {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String idioma;
	
}
