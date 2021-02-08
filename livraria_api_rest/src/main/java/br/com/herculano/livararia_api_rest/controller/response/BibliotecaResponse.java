package br.com.herculano.livararia_api_rest.controller.response;

import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BibliotecaResponse {
	private Integer id;

	private String nome;
	
	private Integer idAdministrador;

	public BibliotecaResponse(Biblioteca entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.idAdministrador = entity.getAdministrador().getId();
	}
}
