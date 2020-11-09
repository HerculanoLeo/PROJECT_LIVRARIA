package br.com.herculano.livararia_api_rest.repository.jpa_repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.repository.custom.UsuarioRespositoryCustom;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, UsuarioRespositoryCustom{

	Optional<Usuario> findByEmail(String username);

}
