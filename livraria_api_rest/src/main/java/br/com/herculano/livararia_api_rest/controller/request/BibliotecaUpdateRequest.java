package br.com.herculano.livararia_api_rest.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BibliotecaUpdateRequest {

	@NotNull
	private Integer id;
	
	@NotBlank
	private String nome;
	
}
