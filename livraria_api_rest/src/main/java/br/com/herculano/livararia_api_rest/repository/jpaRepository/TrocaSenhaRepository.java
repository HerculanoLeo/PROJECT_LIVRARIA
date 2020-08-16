package br.com.herculano.livararia_api_rest.repository.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.TrocaSenha;
import br.com.herculano.livararia_api_rest.repository.custom.TrocaSenhaRepositoryCustom;

public interface TrocaSenhaRepository extends JpaRepository<TrocaSenha, Integer>, TrocaSenhaRepositoryCustom {

}
