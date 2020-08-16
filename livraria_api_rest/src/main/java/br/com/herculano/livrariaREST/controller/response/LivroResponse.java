package br.com.herculano.livrariaREST.controller.response;

import java.time.LocalDate;

import br.com.herculano.livrariaREST.entity.Livro;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LivroResponse {
	private Integer id;

	private String titulo;

	private Long isbn;

	private LocalDate dataLancamento;

	public LivroResponse(Livro entity) {

		this.id = entity.getId();

		this.titulo = entity.getTitulo();

		this.dataLancamento = entity.getDataLancamento();

		this.isbn = entity.getISBN();
	}
}
