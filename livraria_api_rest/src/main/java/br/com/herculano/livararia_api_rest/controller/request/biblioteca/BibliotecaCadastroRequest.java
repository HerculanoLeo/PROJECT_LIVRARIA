package br.com.herculano.livararia_api_rest.controller.request.biblioteca;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BibliotecaCadastroRequest {
	
	@NotBlank
	private String nome;

}
