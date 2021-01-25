package br.com.herculano.livararia_api_rest.controller.response;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.entity.UsuarioCliente;
import lombok.Data;

@Data
public class UsuarioClienteResponse {
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String documento;
	
	private String tipo;

	private String tipoDescricao;
	
	private Perfil perfil;

	public UsuarioClienteResponse(UsuarioCliente entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.email = entity.getEmail();
		this.perfil = entity.getPerfil();
		this.tipo = entity.getDocumento();
		this.tipo = entity.getTipoUsuario();
		this.tipoDescricao = TiposUsuariosEnum.getTipoUsuario(entity.getTipoUsuario()).getDescricao();
	}
}
