package br.com.herculano.livararia_api_rest.controller.request.perfil;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PerfilUpdateRequest {

	@NotBlank
	private String nome;

	@NotBlank
	private String tipo;
	
	private List<PerfilPermissaoConsultaRequest> permissoes;
	
}
