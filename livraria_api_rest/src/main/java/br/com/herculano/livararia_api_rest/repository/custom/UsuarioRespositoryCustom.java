package br.com.herculano.livararia_api_rest.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Usuario;

public interface UsuarioRespositoryCustom { 
	
	Page<Usuario> consultaPorFiltro(UsuarioConsultaRequest entityRequest, Pageable page);

}
