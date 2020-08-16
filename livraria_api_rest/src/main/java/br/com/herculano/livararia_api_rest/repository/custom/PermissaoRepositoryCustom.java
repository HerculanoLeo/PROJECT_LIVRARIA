package br.com.herculano.livararia_api_rest.repository.custom;

import java.util.List;

import br.com.herculano.livararia_api_rest.entity.Permissao;

public interface PermissaoRepositoryCustom {

	List<Permissao> consultaPorIdUsuario(Integer idCliente);
	
}
