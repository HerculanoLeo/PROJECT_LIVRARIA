package br.com.herculano.livararia_api_rest.repository.jpa_repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.repository.custom.PermissaoRepositoryCustom;

public interface PermissaoRepository extends JpaRepository<Permissao, Integer>, PermissaoRepositoryCustom {

	Optional<Permissao> findByCodigo(String codigo);

}
