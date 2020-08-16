package br.com.herculano.livrariaREST.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.herculano.livrariaREST.controller.response.LivroResponse;
import br.com.herculano.livrariaREST.entity.Livro;
import br.com.herculano.livrariaREST.repository.jpaRepository.LivroRepository;

@Service
public class LivroService extends ServiceTemplate<Livro, LivroRepository> {

	@Autowired
	public LivroService(LivroRepository repository) {
		super(repository);
	}

	public List<Livro> consulta() {
		return this.getRepository().findAll();
	}

	public List<Livro> consultaPorIdAutor(Integer idAutor) {
		return getRepository().consultaPorIdAutor(idAutor);
	}

	public List<LivroResponse> converteListaDTO(List<Livro> entities) {
		return entities.stream().map(LivroResponse::new).collect(Collectors.toList());
	}
	
	public Page<LivroResponse> convertePageListaDTO(Page<Livro> entities) {
		return entities.map(LivroResponse::new);
	}

	public void removeAutorPorid(Integer idLivro, Integer idAutor) {
		getRepository().removeAutorPorId(idLivro, idAutor);
	}

}
