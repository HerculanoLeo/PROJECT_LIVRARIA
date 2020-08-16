package br.com.herculano.livrariaREST.controller.response;

import br.com.herculano.livrariaREST.entity.Permissao;
import lombok.Data;

@Data
public class PermissaoResponse {
	
	private String codigo;
	
	public PermissaoResponse(Permissao entity) {
		this.codigo = entity.getCodigo();
	}
}
