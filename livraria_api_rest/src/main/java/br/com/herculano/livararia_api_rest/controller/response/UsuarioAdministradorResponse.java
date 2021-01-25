package br.com.herculano.livararia_api_rest.controller.response;

import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import lombok.Data;

@Data
public class UsuarioAdministradorResponse {

	private Integer id;

	private String nome;

	private String email;

	private String documento;

	private String tipo;
	
	public UsuarioAdministradorResponse(UsuarioAdministrador entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.email = entity.getEmail();
		this.documento = entity.getDocumento();
		this.tipo = entity.getTipoUsuario();
	}

}
