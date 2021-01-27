package br.com.herculano.livararia_api_rest.repository.custom;

import java.util.List;

import br.com.herculano.livararia_api_rest.entity.Livro;

public interface LivroRepositoryCustom {

	public List<Livro> consultaPorIdAutor(Integer idAutor);
	
	public void removeAutorPorId(Integer idLivro, Integer idAutor);

}
