package br.com.herculano.livararia_api_rest.repository.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.repository.custom.AutorRepositoryCustom;

public interface AutorRepository extends JpaRepository<Autor, Integer>, AutorRepositoryCustom {
	
}
