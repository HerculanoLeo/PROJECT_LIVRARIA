package br.com.herculano.livararia_api_rest.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.herculano.livararia_api_rest.controller.request.autor.AutorConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Autor;

public interface AutorRepositoryCustom { 
	
	public Page<Autor> consultaPorIdLivro(Integer idLivro, Pageable page);
	
	public Page<Autor> consultaPorFiltro(AutorConsultaRequest entity, Pageable page);
	
}
