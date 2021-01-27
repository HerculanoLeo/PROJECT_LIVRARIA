package br.com.herculano.livararia_api_rest.controller.response;

import java.time.LocalDate;

import br.com.herculano.livararia_api_rest.entity.Autor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutorResponse {

	private Integer id;
	
	private String nome;
	
	private LocalDate dataNascimento;
	
	private LocalDate dataFalecimento;
	
	public AutorResponse(Autor entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.dataNascimento = entity.getDataNascimento();
		this.dataFalecimento = entity.getDataFalecimento();
	}

}
