package br.com.herculano.livararia_api_rest.controller.request;

import lombok.Data;

@Data
public class UsuarioCadastroRequest {

	private String nome;
	
	private String email;
	
	private String senha;
	
	private String confirmeSenha;
	
	public UsuarioCadastroRequest(String nome, String email, String senha, String confirmeSenha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.confirmeSenha = confirmeSenha;
	}
	
}
