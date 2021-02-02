package br.com.herculano.livararia_api_rest.controller.request;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutorUpdateRequest {

	private Integer id;

	private String nome;

	private LocalDate dataNascimento;

	private LocalDate dataFalecimento;
	
	
}
