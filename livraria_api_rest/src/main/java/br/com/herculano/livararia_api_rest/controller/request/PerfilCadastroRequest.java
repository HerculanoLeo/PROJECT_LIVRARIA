package br.com.herculano.livararia_api_rest.controller.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PerfilCadastroRequest {
	
	@NotBlank
	private String nome;

	@NotBlank
	private String tipo;
	
	private List<PermissaoConsultaRequest> permissoes;

}
