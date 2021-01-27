package br.com.herculano.livararia_api_rest.repository.jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.repository.custom.BibliotecaRespositoryCustom;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Integer>, BibliotecaRespositoryCustom {

}
