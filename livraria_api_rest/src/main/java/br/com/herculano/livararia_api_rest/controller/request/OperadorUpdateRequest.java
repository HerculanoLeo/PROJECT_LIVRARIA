package br.com.herculano.livararia_api_rest.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	
	@NotNull
	private Integer idPerfil;
	
}
