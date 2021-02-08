package br.com.herculano.livararia_api_rest.controller.request.biblioteca;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperadorUpdateRequest {

	@NotBlank
	private String nome;

	@NotBlank
	private String email;

	@NotBlank
	private String documento;

}
