package br.com.herculano.livrariaREST.repository.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.herculano.livrariaREST.entity.GrupoUsuario;
import br.com.herculano.livrariaREST.repository.custom.GrupoRepositoryCustom;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, Integer>, GrupoRepositoryCustom{

}
