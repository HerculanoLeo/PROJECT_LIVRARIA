package br.com.herculano.livararia_api_rest.controller.request;

import lombok.Data;

@Data
public class GrupoUsuarioConsultaRequest {

	private String nome;
	
	private String permissao;
	
}
