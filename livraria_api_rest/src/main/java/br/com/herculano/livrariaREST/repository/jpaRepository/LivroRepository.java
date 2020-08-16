package br.com.herculano.livrariaREST.repository.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livrariaREST.entity.Livro;
import br.com.herculano.livrariaREST.repository.custom.LivroRepositoryCustom;

public interface LivroRepository extends JpaRepository<Livro, Integer>, LivroRepositoryCustom {

}
