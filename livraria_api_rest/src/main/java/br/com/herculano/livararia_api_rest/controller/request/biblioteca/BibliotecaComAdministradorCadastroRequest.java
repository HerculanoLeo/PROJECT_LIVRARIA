package br.com.herculano.livararia_api_rest.controller.request.biblioteca;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibliotecaComAdministradorCadastroRequest {

	@NotBlank
	private String nome;
	
	@NotBlank
	@Size(min = 11, max = 14)
	private String documento;
	
	@NotBlank
	private String email;

	@NotBlank
	private String senha;

	@NotBlank
	private String repetirSenha;

	@NotBlank
	private String nomeBiblioteca;
	
	@NotBlank
	private String idioma;
	
	
}
