package br.com.herculano.livararia_api_rest.controller.request.perfil;

import lombok.Data;

@Data
public class PerfilConsultaRequest {
	
	private Integer idAdministrador;

	private String nome;
	
	private String permissao;
	
}
