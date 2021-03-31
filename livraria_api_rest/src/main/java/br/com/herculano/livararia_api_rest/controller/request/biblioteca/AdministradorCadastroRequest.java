package br.com.herculano.livararia_api_rest.controller.request.biblioteca;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.herculano.livararia_api_rest.entity.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorCadastroRequest {
	
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
	
	@NotBlank
	private String idioma;

	@JsonIgnore
	private Perfil perfil;
	
	public AdministradorCadastroRequest(BibliotecaComAdministradorCadastroRequest entityRequest) {
		this.nome = entityRequest.getNome();
		this.documento = entityRequest.getDocumento();
		this.email = entityRequest.getEmail();
		this.senha = entityRequest.getSenha();
		this.confirmeSenha = entityRequest.getConfirmeSenha();
		this.idioma = entityRequest.getIdioma();
	}
	
}
