package br.com.herculano.livrariaREST.repository.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livrariaREST.entity.TrocaSenha;
import br.com.herculano.livrariaREST.repository.custom.TrocaSenhaRepositoryCustom;

public interface TrocaSenhaRepository extends JpaRepository<TrocaSenha, Integer>, TrocaSenhaRepositoryCustom {

}
