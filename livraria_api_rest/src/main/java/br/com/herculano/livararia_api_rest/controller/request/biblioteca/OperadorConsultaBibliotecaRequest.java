package br.com.herculano.livararia_api_rest.controller.request.biblioteca;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class OperadorConsultaBibliotecaRequest {
	
	@JsonIgnore
	private Integer idBiblioteca;

	private String nomeOperador;

	private String documento;

	private String email;

}
