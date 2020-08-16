package br.com.herculano.livararia_api_rest.controller.response;

import br.com.herculano.livararia_api_rest.entity.Permissao;
import lombok.Data;

@Data
public class PermissaoResponse {
	
	private String codigo;
	
	public PermissaoResponse(Permissao entity) {
		this.codigo = entity.getCodigo();
	}
}
