package br.com.herculano.livararia_api_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.constants.system_message.AutorMessage;
import br.com.herculano.livararia_api_rest.controller.request.AutorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.AutorConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.AutorRepository;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Repository
public class AutorService extends ServiceTemplate<Autor, AutorRepository, AutorMessage> {

	@Autowired
	public AutorService(AutorRepository repository, @Qualifier("AutorMessage") AutorMessage message) {
		super(repository, message);
	}
	
	public Page<Autor> consultaPorIdLivro(Integer idLivro, Pageable page) {
		return getRepository().consultaPorIdLivro(idLivro, page);
	}

	public Page<Autor> consultaPorFiltro(AutorConsultaRequest entity, Pageable page) {
		return getRepository().consultaPorFiltro(entity, page);
	}
	
	public Autor autilizaAutor(Integer id, AutorCadastroRequest entityRequest) {
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
