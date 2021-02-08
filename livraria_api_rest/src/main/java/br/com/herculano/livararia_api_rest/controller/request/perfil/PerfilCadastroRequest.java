package br.com.herculano.livararia_api_rest.controller.request.perfil;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import lombok.Data;

@Data
public class PerfilCadastroRequest {
	
	@NotBlank
	private String nome;

	@NotBlank
	private String tipo;
	
	private List<PerfilPermissaoConsultaRequest> permissoes;
	
	@JsonIgnore
	private UsuarioAdministrador administrador;

}
