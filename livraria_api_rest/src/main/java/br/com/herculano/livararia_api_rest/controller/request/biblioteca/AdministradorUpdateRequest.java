package br.com.herculano.livararia_api_rest.controller.request.biblioteca;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AdministradorUpdateRequest {

	@NotBlank
	private String nome;

	@NotBlank
	private String documento;

	@NotBlank
	private String email;

}
