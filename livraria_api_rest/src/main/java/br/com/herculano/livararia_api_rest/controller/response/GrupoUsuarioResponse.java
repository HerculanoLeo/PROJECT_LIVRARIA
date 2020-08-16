package br.com.herculano.livararia_api_rest.controller.response;

import java.util.List;

import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;
import br.com.herculano.livararia_api_rest.entity.Permissao;
import lombok.Data;

@Data
public class GrupoUsuarioResponse {

	private Integer id;
	
	private String nome;
	
	private List<Permissao> permissoes;
	
	public GrupoUsuarioResponse(GrupoUsuario entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.permissoes = entity.getPermissoes();
	}
	
}
