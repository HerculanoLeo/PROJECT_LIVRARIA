package br.com.herculano.livararia_api_rest.controller.request.biblioteca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibliotecaConsultaRequest {

	private String nomeBiblioteca;
	
	private String nomeAdministrador;
	
}
