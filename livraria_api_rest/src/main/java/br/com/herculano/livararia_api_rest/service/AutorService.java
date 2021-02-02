package br.com.herculano.livararia_api_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.constants.system_message.AutorMessage;
import br.com.herculano.livararia_api_rest.controller.request.AutorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.AutorConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.AutorUpdateRequest;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.AutorRepository;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Repository
public class AutorService extends ServiceTemplate<Autor, AutorRepository, AutorMessage> {
	
	@Autowired
	private BibliotecaService bibliotecaService;

	@Autowired
	public AutorService(AutorRepository repository, AutorMessage message) {
		super(repository, message);
	}
	
	public Autor cadastro(AutorCadastroRequest entityRequest) {
		Biblioteca biblioteca = bibliotecaService.consultaPorId(entityRequest.getIdBiblioteca());
		
		entityRequest.setBiblioteca(biblioteca);
		
		Autor entity = new Autor(entityRequest);

		super.save(entity);

		return entity;
	}
	
	public Autor autilizaAutor(AutorUpdateRequest entityRequest) {
		Autor entity = consultaPorId(entityRequest.getId());
		
		entity.setNome(entityRequest.getNome());
		entity.setDataNascimento(entityRequest.getDataNascimento());
		entity.setDataFalecimento(entityRequest.getDataFalecimento());
		
		save(entity);
		
		return entity;		
	}
	
	public Page<Autor> consultaPorIdLivro(Integer idLivro, Pageable page) {
		return getRepository().consultaPorIdLivro(idLivro, page);
	}

	public Page<Autor> consultaPorFiltro(AutorConsultaRequest entity, Pageable page) {
		return getRepository().consultaPorFiltro(entity, page);
	}

	public void delete(Integer id) {
		Autor entity = consultaPorId(id);
		
		super.delete(entity);
	}


}
