package br.com.herculano.livararia_api_rest.controller.request;

import java.util.List;

import lombok.Data;

@Data
public class PerfilCadastroRequest {
	
	private String nome;

	private String tipo;
	
	private List<PermissaoConsultaRequest> permissoes;

}
