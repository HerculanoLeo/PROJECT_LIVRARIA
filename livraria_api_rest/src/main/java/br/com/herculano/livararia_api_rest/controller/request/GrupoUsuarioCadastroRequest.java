package br.com.herculano.livararia_api_rest.controller.request;

import java.util.List;

import lombok.Data;

@Data
public class GrupoUsuarioCadastroRequest {
	
	private String nome;
	
	private List<PermissaoRequest> permissoes;

}
