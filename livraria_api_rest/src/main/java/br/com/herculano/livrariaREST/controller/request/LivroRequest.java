package br.com.herculano.livrariaREST.controller.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LivroRequest {
	private String titulo;
	
	private Long isbn;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  
	private LocalDate dataLancamento;
	
	private List<Integer> idsAutor;
}
