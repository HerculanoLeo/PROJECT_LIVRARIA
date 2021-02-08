package br.com.herculano.livararia_api_rest.controller.request.autor;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import lombok.Data;

@Data
public class AutorCadastroRequest {
	
	@NotNull
	private Integer idBiblioteca;

	@NotBlank
	private String nome;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  
	private LocalDate dataNascimento;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  
	private LocalDate dataFalecimento;
	
	@JsonIgnore
	private Biblioteca biblioteca;
	
}
