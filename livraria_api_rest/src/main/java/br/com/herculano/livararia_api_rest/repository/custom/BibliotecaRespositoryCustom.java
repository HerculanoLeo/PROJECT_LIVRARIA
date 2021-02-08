package br.com.herculano.livararia_api_rest.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.herculano.livararia_api_rest.controller.request.biblioteca.BibliotecaConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Biblioteca;

public interface BibliotecaRespositoryCustom {

	Page<Biblioteca> consultaPorFiltro(BibliotecaConsultaRequest entityRequest, Pageable page);

}
