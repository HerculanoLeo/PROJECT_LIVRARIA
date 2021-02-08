package br.com.herculano.livararia_api_rest.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.herculano.livararia_api_rest.controller.request.biblioteca.OperadorConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;

public interface UsuarioOperadorRepositoryCustom {

	public Page<UsuarioOperador> consulta(OperadorConsultaRequest entityRequest, Pageable page);
	
}
