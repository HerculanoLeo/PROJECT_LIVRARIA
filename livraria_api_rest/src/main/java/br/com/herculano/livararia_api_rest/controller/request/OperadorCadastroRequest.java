package br.com.herculano.livararia_api_rest.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperadorCadastroRequest {

	@NotNull
	private Integer idBiblioteca;

	@NotBlank
	private String nome;
	
	@NotBlank
	private String email;

	@NotBlank
	private String documento;
	
	@NotNull
	private Integer idPerfil;
	
	@JsonIgnore
	private String senha;

	@JsonIgnore
	private Perfil perfil;
	
	@JsonIgnore
	private Biblioteca biblioteca;

}
