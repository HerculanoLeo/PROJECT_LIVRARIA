package br.com.herculano.livararia_api_rest.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.herculano.livararia_api_rest.controller.request.BibliotecaCadastroRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_biblioteca")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Biblioteca {
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_administradora")
	@SequenceGenerator(name = "sq_administradora", sequenceName = "sq_administradora", allocationSize = 1)
	private Integer id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "id_usuario_administrador")
	private Usuario usuarioAdministrador;
	
	@OneToMany
	@JoinTable(name = "tb_biblioteca_operador", joinColumns = {
			@JoinColumn(name = "id_biblioteca", referencedColumnName = "id")}, inverseJoinColumns = {
					@JoinColumn(name = "id_operador", referencedColumnName = "id")}, uniqueConstraints = {
							@UniqueConstraint(columnNames = {"id_operador"})})
	private List<Usuario> operadores;
	
	public Biblioteca(BibliotecaCadastroRequest entityRequest, Usuario usuario) {
		this.nome = entityRequest.getNomeBiblioteca();
		this.usuarioAdministrador = usuario;
	}

}
