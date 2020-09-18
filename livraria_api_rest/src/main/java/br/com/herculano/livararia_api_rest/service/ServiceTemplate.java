package br.com.herculano.livararia_api_rest.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("rawtypes")
public class ServiceTemplate<E, JPA> {

	protected JpaRepository repository;

	public ServiceTemplate(JpaRepository repository) {
		this.repository = repository;
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
			throw new EntityNotFoundException("");
		}
		
		return (E) optional.get();
	}
	
	@SuppressWarnings("unchecked")
	protected JPA getRepository() {
		return (JPA) repository;
	}

}
