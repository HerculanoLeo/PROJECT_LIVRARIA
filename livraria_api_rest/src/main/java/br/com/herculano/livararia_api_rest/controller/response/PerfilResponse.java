package br.com.herculano.livararia_api_rest.controller.response;

import java.util.List;
import java.util.stream.Collectors;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import lombok.Data;

@Data
public class PerfilResponse {

	private Integer id;
	
	private String nome;
	
	private String tipo;

	private String tipoDescricao;
	
	private List<PermissaoResponse> permissoes;

	public PerfilResponse(Perfil entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.tipo = entity.getTipo();
		this.permissoes = entity.getPermissoes().stream().map(PermissaoResponse::new).collect(Collectors.toList());
		this.tipoDescricao = TiposUsuariosEnum.getTipoUsuario(entity.getTipo()).getDescricao();
	}
}
