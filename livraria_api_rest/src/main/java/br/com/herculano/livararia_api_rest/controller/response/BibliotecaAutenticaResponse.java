package br.com.herculano.livararia_api_rest.controller.response;

import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibliotecaAutenticaResponse {

	private Integer id;
	
	private String nome;
	
	public BibliotecaAutenticaResponse(Biblioteca entity) {
		this.id = entity.getId();
	}
	
	
	
}
