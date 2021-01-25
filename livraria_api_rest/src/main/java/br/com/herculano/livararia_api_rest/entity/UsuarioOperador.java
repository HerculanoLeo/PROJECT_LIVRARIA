package br.com.herculano.livararia_api_rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaOperadorCadastroRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_usuario_operador")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class UsuarioOperador extends Usuario {

	private static final long serialVersionUID = -4038440784192427098L;
	
	@ManyToOne
	@JoinColumn(name = "id_biblioteca", referencedColumnName = "id", nullable = false)
	private Biblioteca biblioteca;

	@Column(name = "documento", nullable = false)
	private String documento;
	
	public UsuarioOperador(BibliotecaOperadorCadastroRequest entityRequest) {
		super(entityRequest.getNome(), entityRequest.getEmail(), entityRequest.getSenha(), TiposUsuariosEnum.OPERADOR.getValor() ,entityRequest.getPerfil());
		
		this.documento = entityRequest.getDocumento();
		this.biblioteca = entityRequest.getBiblioteca();
	}
	
}
