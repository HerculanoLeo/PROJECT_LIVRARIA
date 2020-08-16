package br.com.herculano.livrariaREST.repository.jpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livrariaREST.entity.Usuario;
import br.com.herculano.livrariaREST.repository.custom.UsuarioRespositoryCustom;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, UsuarioRespositoryCustom{

	Optional<Usuario> findByEmail(String username);

}
