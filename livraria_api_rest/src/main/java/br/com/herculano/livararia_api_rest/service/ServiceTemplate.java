package br.com.herculano.livararia_api_rest.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("rawtypes")
public class ServiceTemplate<E, JPA> {

	private JpaRepository repository;

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
	public Page<E> consultaPorFiltro(E filterEntity, Pageable page) {
		return repository.findAll(Example.of(filterEntity), page);
	}

	@SuppressWarnings("unchecked")
	public Optional<E> consultaPorId(Integer id) {
		return repository.findById(id);
	}
	
	@SuppressWarnings("unchecked")
	protected JPA getRepository() {
		return (JPA) this.repository;
	}

}
