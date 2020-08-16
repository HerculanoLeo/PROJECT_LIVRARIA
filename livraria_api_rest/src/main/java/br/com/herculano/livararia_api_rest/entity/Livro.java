package br.com.herculano.livararia_api_rest.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.herculano.livararia_api_rest.controller.request.LivroRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_livro")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LIVRO")
	@SequenceGenerator(name = "SQ_LIVRO", sequenceName = "SQ_LIVRO", allocationSize = 1)
	private Integer id;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "isbn", nullable = false, unique = true, length = 13)
	private Long ISBN;

	@Column(name = "dt_lancamento", nullable = false)
	private LocalDate dataLancamento;

	@ManyToMany
	@JoinTable(name = "tb_livro_autor", joinColumns = {
			@JoinColumn(name = "id_livro", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_autor", referencedColumnName = "id") }, 
					uniqueConstraints = {@UniqueConstraint(
							columnNames = {"id_livro", "id_autor"})})
	private List<Autor> autores;

	public Livro(LivroRequest entity) {

		this.titulo = entity.getTitulo();

		this.ISBN = entity.getIsbn();

		this.dataLancamento = entity.getDataLancamento();
	}
}
