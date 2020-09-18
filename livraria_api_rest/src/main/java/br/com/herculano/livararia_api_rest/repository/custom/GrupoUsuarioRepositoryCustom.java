package br.com.herculano.livararia_api_rest.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.herculano.livararia_api_rest.controller.request.GrupoUsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;

public interface GrupoUsuarioRepositoryCustom {
	
	public Page<GrupoUsuario> consultaPorFiltro(GrupoUsuarioConsultaRequest filter, Pageable page);
	
}
