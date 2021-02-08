package br.com.herculano.livararia_api_rest.repository.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.herculano.livararia_api_rest.controller.request.livro.LivroConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Livro;

public interface LivroRepositoryCustom {
	
	Page<Livro> consulta(LivroConsultaRequest entityRequest, Pageable page);

	public List<Livro> consultaPorIdAutor(Integer idAutor);
	
}
