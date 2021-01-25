package br.com.herculano.livararia_api_rest.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.herculano.livararia_api_rest.controller.request.BibliotecaCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaComAdministradorCadastroRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_biblioteca")
public class Biblioteca implements Serializable {

	private static final long serialVersionUID = 146582218570096099L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_BIBLIOTECA")
	@SequenceGenerator(name = "SQ_BIBLIOTECA", sequenceName = "SQ_BIBLIOTECA", allocationSize = 1)
	private Integer id;

	@Column(name = "nome", nullable = false)
	private String nome;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_administrador")
	private UsuarioAdministrador administrador;

	@Transient
	private List<UsuarioOperador> operadores;

	public Biblioteca(BibliotecaComAdministradorCadastroRequest entityRequest, UsuarioAdministrador administrador) {
		this.nome = entityRequest.getNomeBiblioteca();
		this.administrador = administrador;
	}

	public Biblioteca(BibliotecaCadastroRequest entityRequest, UsuarioAdministrador administrador) {
		this.nome = entityRequest.getNome();
		this.administrador = administrador;
	}

}
