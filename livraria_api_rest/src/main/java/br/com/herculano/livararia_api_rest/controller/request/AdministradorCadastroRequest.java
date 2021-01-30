package br.com.herculano.livararia_api_rest.controller.request;

import br.com.herculano.livararia_api_rest.entity.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorCadastroRequest {
	
	private String nome;
	
	private String documento;
	
	private String email;

	private String senha;

	private String confirmeSenha;
	
	private String idioma;
	
	private Perfil perfil;
	
	public AdministradorCadastroRequest(BibliotecaComAdministradorCadastroRequest entityRequest) {
		this.nome = entityRequest.getNome();
		this.documento = entityRequest.getDocumento();
		this.email = entityRequest.getEmail();
		this.senha = entityRequest.getSenha();
		this.confirmeSenha = entityRequest.getRepetirSenha();
		this.idioma = entityRequest.getIdioma();
	}
	
}
