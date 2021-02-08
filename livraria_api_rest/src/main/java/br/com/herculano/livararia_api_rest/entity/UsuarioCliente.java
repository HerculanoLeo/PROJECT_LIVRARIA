package br.com.herculano.livararia_api_rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioClienteCadastroRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tb_usuario_cliente")
@PrimaryKeyJoinColumn(name = "id_usuario", referencedColumnName = "id")
public class UsuarioCliente extends Usuario {

	private static final long serialVersionUID = -4373959223887551417L;

	@Column(name = "documento", nullable = false)
	private String documento;

	public UsuarioCliente(String nome, String email, String password, String valor, Perfil perfil, String documento, String idioma) {
		super(nome, email, password, valor, idioma, perfil);
		this.documento = documento;
	}

	public UsuarioCliente(UsuarioClienteCadastroRequest entityRequest) {
		super(entityRequest.getNome(), entityRequest.getEmail(), entityRequest.getSenha(), entityRequest.getTipo(), entityRequest.getIdioma(), entityRequest.getPerfil());
		this.documento = entityRequest.getDocumento();
	}

}
