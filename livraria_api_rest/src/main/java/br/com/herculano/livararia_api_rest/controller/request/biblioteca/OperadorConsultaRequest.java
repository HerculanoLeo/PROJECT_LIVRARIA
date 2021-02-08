package br.com.herculano.livararia_api_rest.controller.request.biblioteca;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperadorConsultaRequest {
	
	private Integer idAdministrador;

	private Integer idBiblioteca;
	
	private String nomeOperador;
	
	private String documento;

	private String email;

	private String nomeBiblioteca;
	
}
