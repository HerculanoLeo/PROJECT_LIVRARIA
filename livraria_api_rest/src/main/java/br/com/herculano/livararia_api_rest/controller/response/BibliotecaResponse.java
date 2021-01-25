package br.com.herculano.livararia_api_rest.controller.response;

import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BibliotecaResponse {

	private Integer id;
	
	private String nome;
	
	private UsuarioAdministradorResponse usuarioAdministrador;
	
	public BibliotecaResponse(Biblioteca entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.usuarioAdministrador = new UsuarioAdministradorResponse(entity.getAdministrador());
	}
	
}
