package br.com.herculano.livrariaREST.controller.request;

import lombok.Data;

@Data
public class ValidaCodigoRequest {

	private String email;
	
	private String codigo;
	
}
