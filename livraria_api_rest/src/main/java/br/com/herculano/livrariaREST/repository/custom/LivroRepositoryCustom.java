package br.com.herculano.livrariaREST.repository.custom;

import java.util.List;

import br.com.herculano.livrariaREST.entity.Livro;

public interface LivroRepositoryCustom {

	public List<Livro> consultaPorIdAutor(Integer idAutor);
	
	public void removeAutorPorId(Integer idLivro, Integer idAutor);
}
