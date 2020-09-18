package br.com.herculano.livararia_api_rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.controller.request.AutorRequest;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.repository.jpaRepository.AutorRepository;

@Repository
public class AutorService extends ServiceTemplate<Autor, AutorRepository> {

	@Autowired
	public AutorService(AutorRepository repository) {
		super(repository);
	}
	
	public List<Autor> consultaPorIdLivro(Integer idLivro) {
		return getRepository().consultaPorIdLivro(idLivro);
	}

	public Autor autilizaAutor(Integer id, AutorRequest entityRequest) {
		Autor entity = this.consultaPorId(id);

		entity.setNome(entityRequest.getNome());
		entity.setDataNascimento(entityRequest.getDataNascimento());
		entity.setDataFalecimento(entityRequest.getDataFalecimento());
		
		save(entity);

		return entity;		
	}

	public void delete(Integer id) {
		Autor entity = consultaPorId(id);
		
		super.delete(entity);
	}

}
