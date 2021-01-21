package br.com.herculano.livararia_api_rest.repository.jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;

public interface OperadorRepository  extends JpaRepository<UsuarioOperador, Integer>  {

}
