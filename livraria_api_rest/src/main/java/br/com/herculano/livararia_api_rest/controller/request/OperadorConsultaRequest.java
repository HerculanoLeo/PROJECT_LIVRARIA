package br.com.herculano.livararia_api_rest.controller.request;

import lombok.Data;

@Data
public class OperadorConsultaRequest {

	private Integer idBiblioteca;
	
	private String nomeOperador;
	
	private String documento;

	private String email;

	private String nomeBiblioteca;
	
}
