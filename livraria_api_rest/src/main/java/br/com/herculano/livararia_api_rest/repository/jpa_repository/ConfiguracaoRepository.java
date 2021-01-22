package br.com.herculano.livararia_api_rest.repository.jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.Configuracao;

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, String> {

}
