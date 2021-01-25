package br.com.herculano.livararia_api_rest.controller.response;

import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;
import lombok.Data;

@Data
public class UsuarioOperadorResponse {

	private Integer id;

	private String nome;

	private String email;

	private String documento;

	private String tipo;
	
	private PerfilResponse perfil;
	
	public UsuarioOperadorResponse(UsuarioOperador entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.email = entity.getEmail();
		this.documento = entity.getDocumento();
		this.tipo = entity.getTipoUsuario();
		this.perfil = new PerfilResponse(entity.getPerfil());
	}
	
}
