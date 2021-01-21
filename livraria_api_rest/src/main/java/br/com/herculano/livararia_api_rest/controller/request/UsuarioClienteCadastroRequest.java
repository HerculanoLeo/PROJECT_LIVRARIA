package br.com.herculano.livararia_api_rest.controller.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.herculano.livararia_api_rest.entity.Perfil;
import lombok.Data;

@Data
public class UsuarioClienteCadastroRequest {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String documento;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String senha;
	
	@NotBlank
	private String confirmeSenha;
	
	@JsonIgnore
	private String tipo;
	
	@JsonIgnore
	private Perfil perfil;
	
	public UsuarioClienteCadastroRequest(String nome, String email, String senha, String confirmeSenha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.confirmeSenha = confirmeSenha;
	}
	
}
