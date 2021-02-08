package br.com.herculano.livararia_api_rest.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.herculano.livararia_api_rest.controller.request.autor.AutorCadastroRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_autor")
public class Autor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AUTOR")
	@SequenceGenerator(name = "SQ_AUTOR", sequenceName = "SQ_AUTOR", allocationSize = 1)
	private Integer id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "dt_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(name = "dt_falecimento", nullable = true)
	private LocalDate dataFalecimento;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_biblioteca", referencedColumnName = "id")
	private Biblioteca biblioteca;

	@ManyToMany
	@JoinTable(name = "tb_livro_autor", joinColumns = {
			@JoinColumn(name = "id_autor", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_livro", referencedColumnName = "id") })
	private List<Livro> livros;

	public Autor(Integer id, String nome, LocalDate dataNascimento, LocalDate dataFalecimento,
			ArrayList<Livro> livros) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.dataFalecimento = dataFalecimento;
		this.livros = livros;
	}

	public Autor(AutorCadastroRequest entityForm) {
		this.nome = entityForm.getNome();
		this.dataNascimento = entityForm.getDataNascimento();
		this.dataFalecimento = entityForm.getDataFalecimento();
		this.biblioteca = entityForm.getBiblioteca();
	}

}
