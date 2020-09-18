package br.com.herculano.livararia_api_rest.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;

public interface GrupoUsuarioRepositoryCustom {
	
	public Page<GrupoUsuario> consultaPorFiltro(GrupoUsuario filter, Pageable page);
	
}
