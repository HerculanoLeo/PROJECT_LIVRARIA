package br.com.herculano.livararia_api_rest.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.herculano.livararia_api_rest.controller.request.perfil.PerfilConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Perfil;

public interface PerfilRepositoryCustom {
	
	public Page<Perfil> consultaPorFiltro(PerfilConsultaRequest entityRequest, Pageable page);
	
}
