package br.com.herculano.livararia_api_rest.repository.jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;
import br.com.herculano.livararia_api_rest.repository.custom.UsuarioOperadorRepositoryCustom;

public interface UsuarioOperadorRepository extends JpaRepository<UsuarioOperador, Integer>, UsuarioOperadorRepositoryCustom {

}
