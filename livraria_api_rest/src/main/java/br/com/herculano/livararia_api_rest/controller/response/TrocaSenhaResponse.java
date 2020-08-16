package br.com.herculano.livararia_api_rest.controller.response;

import java.time.LocalDateTime;

import br.com.herculano.livararia_api_rest.entity.TrocaSenha;
import lombok.Data;

@Data
public class TrocaSenhaResponse {

	private String nome;
	
	private String email;
	
	private LocalDateTime dataValidade;
	
	public TrocaSenhaResponse(TrocaSenha trocaSenha) {
		this.nome = trocaSenha.getNome();
		this.email = trocaSenha.getEmail();
		this.dataValidade = trocaSenha.getDataValidade();
	}
	
}
