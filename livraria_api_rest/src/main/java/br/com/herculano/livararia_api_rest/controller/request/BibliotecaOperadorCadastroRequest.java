package br.com.herculano.livararia_api_rest.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibliotecaOperadorCadastroRequest {

	@NotBlank
	private String nomeOperador;
	
	@NotBlank
	private String email;

	@NotBlank
	private String documento;
	
	@NotBlank
	private Integer idPerfil;
}
