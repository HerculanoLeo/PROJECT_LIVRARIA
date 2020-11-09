package br.com.herculano.livararia_api_rest.repository.jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.Livro;
import br.com.herculano.livararia_api_rest.repository.custom.LivroRepositoryCustom;

public interface LivroRepository extends JpaRepository<Livro, Integer>, LivroRepositoryCustom {

}
