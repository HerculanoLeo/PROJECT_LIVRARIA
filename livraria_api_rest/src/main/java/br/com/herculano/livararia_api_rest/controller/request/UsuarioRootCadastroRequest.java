package br.com.herculano.livararia_api_rest.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.herculano.livararia_api_rest.entity.Perfil;
import lombok.Data;

@Data
public class UsuarioRootCadastroRequest {

	@NotBlank
	private String nome;

	@NotBlank
	private String email;
	
	@NotNull
	private Integer idPerfil;
	
	@NotBlank
	private String idioma;

	@JsonIgnore
	private String senha;

	@JsonIgnore
	private Perfil perfil;

	public UsuarioRootCadastroRequest(String nome, String email, String idioma) {
		this.nome = nome;
		this.email = email;
		this.idioma = idioma;
	}

}
