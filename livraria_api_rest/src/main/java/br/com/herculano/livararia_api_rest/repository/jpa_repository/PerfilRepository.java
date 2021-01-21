package br.com.herculano.livararia_api_rest.repository.jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.repository.custom.PerfilRepositoryCustom;

public interface PerfilRepository extends JpaRepository<Perfil, Integer>, PerfilRepositoryCustom{

}
