package br.com.herculano.livrariaREST.repository.custom;

import java.util.List;

import br.com.herculano.livrariaREST.entity.Permissao;

public interface PermissaoRepositoryCustom {

	List<Permissao> consultaPorIdUsuario(Integer idCliente);
	
}
