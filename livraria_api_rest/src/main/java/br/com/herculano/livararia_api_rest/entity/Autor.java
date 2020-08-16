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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.herculano.livararia_api_rest.controller.request.AutorRequest;
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
	
	@ManyToMany
	@JoinTable(name = "tb_livro_autor", joinColumns = {
			@JoinColumn(name = "id_autor", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_livro", referencedColumnName = "id") })
	private List<Livro> livros;

	public Autor(Integer id, String nome, LocalDate dataNascimento, LocalDate dataFalecimento, ArrayList<Livro> livros) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.dataFalecimento = dataFalecimento;
		this.livros = livros;
	}

	public Autor(AutorRequest entityForm) {
		this.nome = entityForm.getNome();
		this.dataNascimento = entityForm.getDataNascimento();
		this.dataFalecimento = entityForm.getDataFalecimento();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autor other = (Autor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
}
