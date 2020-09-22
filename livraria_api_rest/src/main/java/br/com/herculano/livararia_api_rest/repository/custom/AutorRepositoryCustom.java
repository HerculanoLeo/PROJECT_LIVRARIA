package br.com.herculano.livararia_api_rest.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.herculano.livararia_api_rest.controller.request.AutorConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Autor;

//O Padrão de nomenclatura deve ser respeitado "EntityRepositoryCustom"
public interface AutorRepositoryCustom { 
	
	public Page<Autor> consultaPorIdLivro(Integer idLivro, Pageable page);
	
	public Page<Autor> consultaPorFiltro(AutorConsultaRequest entity, Pageable page);
	
}
