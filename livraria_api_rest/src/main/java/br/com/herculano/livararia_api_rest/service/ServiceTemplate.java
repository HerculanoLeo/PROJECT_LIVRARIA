package br.com.herculano.livararia_api_rest.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.constants.system_message.CommonMessage;
import br.com.herculano.livararia_api_rest.constants.system_message.MessageTemplate;

@SuppressWarnings("rawtypes")
public class ServiceTemplate<E, JPA, M extends MessageTemplate> {

	protected JpaRepository repository;
	
	protected M message;

	public ServiceTemplate(JpaRepository repository, M message) {
		this.repository = repository;
		this.message = message;
	}

	@SuppressWarnings("unchecked")
	public void save(E entity) {
		repository.save(entity);
	}

	@SuppressWarnings("unchecked")
	public void delete(E entity) {
		repository.delete(entity);
	}

	@SuppressWarnings("unchecked")
	public Page<E> consulta(Pageable page) {
		return repository.findAll(page);
	}

	@SuppressWarnings("unchecked")
	public Optional<E> findById(Integer id) {
		return repository.findById(id);
	}

	@SuppressWarnings("unchecked")
	public E consultaPorId(Integer id) {

		Optional optional = repository.findById(id);

		if (!optional.isPresent()) {
			Object[] args = {id};
			
			throw new EntityNotFoundException(CommonMessage.getCodigo(message.getNotFound(), args));
		}

		return (E) optional.get();
	}

	@SuppressWarnings("unchecked")
	protected JPA getRepository() {
		return (JPA) repository;
	}
	
	protected M getMessage() {
		return (M) message;
	}

}
