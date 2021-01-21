package br.com.herculano.livararia_api_rest.controller.response;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import lombok.Data;

@Data
public class UsuarioPerfilResponse {
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String tipo;
	
	private Perfil perfil;

	public UsuarioPerfilResponse(Usuario entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.email = entity.getEmail();
		this.perfil = entity.getPerfil();
		this.tipo = TiposUsuariosEnum.getTipoUsuario(entity.getTipoUsuario()).getDescricao();
	}
}
