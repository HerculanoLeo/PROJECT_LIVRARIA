package br.com.herculano.livrariaREST.controller.response;

import java.util.List;

import br.com.herculano.livrariaREST.entity.GrupoUsuario;
import br.com.herculano.livrariaREST.entity.Permissao;
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
