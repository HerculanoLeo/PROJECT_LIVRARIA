package br.com.herculano.livrariaREST.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.herculano.livrariaREST.entity.Autor;
import br.com.herculano.livrariaREST.repository.jpaRepository.AutorRepository;

@Repository
public class AutorService extends ServiceTemplate<Autor, AutorRepository> {

	@Autowired
	public AutorService(AutorRepository repository) {
		super(repository);
	}

	public List<Autor> consultaPorIdLivro(Integer idLivro) {
		return getRepository().consultaPorIdLivro(idLivro);
	}
	
}
