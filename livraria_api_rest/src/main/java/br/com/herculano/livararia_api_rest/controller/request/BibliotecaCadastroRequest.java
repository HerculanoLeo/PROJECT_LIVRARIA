package br.com.herculano.livararia_api_rest.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibliotecaCadastroRequest {

	@NotBlank
	private String nomeBiblioteca;
	
	@NotBlank
	private String nomeAdministrador;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String senha;
	
	@NotBlank
	private String confirmeSenha;
	
	
}
