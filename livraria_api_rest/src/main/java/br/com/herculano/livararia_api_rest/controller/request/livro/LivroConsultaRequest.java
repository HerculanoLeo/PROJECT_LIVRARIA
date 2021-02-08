package br.com.herculano.livararia_api_rest.controller.request.livro;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LivroConsultaRequest {
	
	private Integer id;
	
	private String titulo;
	
	private Long ISBN;
	
	private LocalDate dataLancamentoInicio;

	private LocalDate dataLancamentoFim;
	
	private Integer idBiblioteca;
	
	private String nomeBiblioteca;
	
	private String nomeAutor;

}
