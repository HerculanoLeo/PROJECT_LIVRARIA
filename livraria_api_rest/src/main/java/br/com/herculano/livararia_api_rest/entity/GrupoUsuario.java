package br.com.herculano.livararia_api_rest.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_grupo_usuario")
public class GrupoUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_GRUPO_USUARIO")
	@SequenceGenerator(name = "SQ_GRUPO_USUARIO", sequenceName = "SQ_GRUPO_USUARIO", allocationSize = 1)
	private Integer id;
	
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "tb_grupo_usuario_permissao", joinColumns =
				@JoinColumn (name = "id_grupo_usuario", referencedColumnName = "id"), inverseJoinColumns = 
						@JoinColumn (name = "id_permissao", referencedColumnName = "codigo"))
	private List<Permissao> permissoes;
	
}
