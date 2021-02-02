package br.com.herculano.livararia_api_rest.controller.request;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LivroAdicionaAutoresRequest {

	private Integer id;
	
	private Integer idBiblioteca;
	
	private List<Integer> idAutores;
	
}
