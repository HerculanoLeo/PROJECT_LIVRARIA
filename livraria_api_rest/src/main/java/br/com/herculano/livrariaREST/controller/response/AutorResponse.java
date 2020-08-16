package br.com.herculano.livrariaREST.controller.response;

import java.time.LocalDate;

import br.com.herculano.livrariaREST.entity.Autor;
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
