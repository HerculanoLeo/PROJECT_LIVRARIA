package br.com.herculano.livararia_api_rest.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_perfil")
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PERFIL")
	@SequenceGenerator(name = "SQ_PERFIL", sequenceName = "SQ_PERFIL", allocationSize = 1)
	private Integer id;
	
	private String nome;
	
	@ManyToMany
	@JoinTable(name = "tb_perfil_permissao", joinColumns =
				@JoinColumn (name = "id_perfil", referencedColumnName = "id"), inverseJoinColumns = 
						@JoinColumn (name = "id_permissao", referencedColumnName = "codigo"))
	private List<Permissao> permissoes;
	
	@Column(name = "tipo", nullable = false)
	private String tipo;
	
	@Column(name = "padrao", nullable = false, columnDefinition = "boolean default false", updatable = false)
	private Boolean padrao = false;
	
	@JsonIgnore
	public boolean isROOT() {
		return tipo.equals(TiposUsuariosEnum.ROOT.getValor()) || isBiblioteca() || isCliente();
	}

	@JsonIgnore
	private boolean isBiblioteca() {
		return tipo.equals(TiposUsuariosEnum.ADMINISTRADOR.getValor()) || tipo.equals(TiposUsuariosEnum.OPERADOR.getValor()) ||isCliente();
	}

	@JsonIgnore
	private boolean isCliente() {
		return tipo.equals(TiposUsuariosEnum.CLIENTE.getValor());
	}
	
}
