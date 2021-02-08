package br.com.herculano.livararia_api_rest.controller.request.livro;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import lombok.Data;

@Data
public class LivroCadastroRequest {
	
	@NotNull
	private Integer idBiblioteca;
	
	@NotBlank
	private String titulo;
	
	@NotNull
	private Long isbn;
	
	@NotNull
	@PastOrPresent
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  
	private LocalDate dataLancamento;
	
	private List<Integer> idAutores;
	
	@JsonIgnore
	private Biblioteca biblioteca;

}
