package br.com.herculano.livararia_api_rest.controller.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AutorConsultaRequest {
	
	private String nome;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataNascimento;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataFalecimento;
	
	private String nomeLivro;
	
	private String nomeBiblioteca;
	
	private Integer idBiblioteca;

}
