package br.com.herculano.livararia_api_rest.controller.request;

import lombok.Data;

@Data
public class ValidaCodigoRequest {

	private String email;
	
	private String codigo;
	
}
