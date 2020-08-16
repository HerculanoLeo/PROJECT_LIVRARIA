package br.com.herculano.livrariaREST.repository.jpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livrariaREST.entity.Permissao;
import br.com.herculano.livrariaREST.repository.custom.PermissaoRepositoryCustom;

public interface PermissaoRepository extends JpaRepository<Permissao, Integer>, PermissaoRepositoryCustom {

	Optional<Permissao> findByCodigo(String codigo);


}
