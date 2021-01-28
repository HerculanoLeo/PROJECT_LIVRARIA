package br.com.herculano.livararia_api_rest.controller.response;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import lombok.Data;

@Data
public class PerfilResponse {

	private Integer id;
	
	private String nome;
	
	private String tipo;

	private String tipoDescricao;

	public PerfilResponse(Perfil entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.tipo = entity.getTipo();
		this.tipoDescricao = TiposUsuariosEnum.getTipoUsuario(entity.getTipo()).getDescricao();
	}
}
