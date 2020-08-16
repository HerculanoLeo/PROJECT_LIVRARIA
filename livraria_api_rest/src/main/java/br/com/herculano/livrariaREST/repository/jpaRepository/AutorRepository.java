package br.com.herculano.livrariaREST.repository.jpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livrariaREST.entity.Autor;
import br.com.herculano.livrariaREST.repository.custom.AutorRepositoryCustom;

public interface AutorRepository extends JpaRepository<Autor, Integer>, AutorRepositoryCustom {

	public List<Autor> consultaPorIdLivro(Integer idLivro);
	
}
