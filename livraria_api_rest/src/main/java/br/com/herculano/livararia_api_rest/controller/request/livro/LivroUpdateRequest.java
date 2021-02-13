package br.com.herculano.livararia_api_rest.controller.request.livro;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LivroUpdateRequest {

	@NotBlank
	private String titulo;
	
	@NotNull
	private Long isbn;
	
	@NotNull
	@PastOrPresent
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  
	private LocalDate dataLancamento;
	
	private List<Integer> idAutores;
	
}
