package br.com.herculano.livararia_api_rest.controller.request;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LivroCadastroRequest {
	
	@NotNull
	@NotEmpty
	private String titulo;
	
	@NotNull
	private Long isbn;
	
	@NotNull
	@PastOrPresent
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  
	private LocalDate dataLancamento;
	
	private List<Integer> idsAutor;

}
