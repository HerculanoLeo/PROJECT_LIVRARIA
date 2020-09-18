package br.com.herculano.livararia_api_rest.repository.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;
import br.com.herculano.livararia_api_rest.repository.custom.GrupoUsuarioRepositoryCustom;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, Integer>, GrupoUsuarioRepositoryCustom{
	
	

}
