package br.com.herculano.livararia_api_rest.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.herculano.livararia_api_rest.entity.Perfil;
import lombok.Data;

@Data
public class UsuarioRootCadastroRequest {

	private String nome;

	private String email;
	
	private Integer idPerfil;

	@JsonIgnore
	private String tipo;

	@JsonIgnore
	private String senha;

	@JsonIgnore
	private Perfil perfil;

	public UsuarioRootCadastroRequest(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}

}
