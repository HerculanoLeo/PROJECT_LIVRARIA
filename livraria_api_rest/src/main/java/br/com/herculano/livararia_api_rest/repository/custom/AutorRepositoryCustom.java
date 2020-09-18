package br.com.herculano.livararia_api_rest.repository.custom;

import java.util.List;

import br.com.herculano.livararia_api_rest.entity.Autor;

//O Padrão de nomenclatura deve ser respeitado "EntityRepositoryCustom"
public interface AutorRepositoryCustom { 
	
	public List<Autor> consultaPorIdLivro(Integer idLivro);
	
}
