package br.com.herculano.livararia_api_rest.controller.response;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import lombok.Data;

@Data
public class UsuarioResponse {
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String tipo;

	private String tipoDescricao;
	
	private UsuarioPerfilResponse perfil;

	public UsuarioResponse(Usuario entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.email = entity.getEmail();
		this.perfil = new UsuarioPerfilResponse(entity.getPerfil());
		this.tipo = entity.getTipoUsuario();
		this.tipoDescricao = TiposUsuariosEnum.getTipoUsuario(entity.getTipoUsuario()).getDescricao();
	}
}
