package br.com.herculano.livararia_api_rest.repository.jpa_repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.repository.custom.UsuarioRespositoryCustom;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, UsuarioRespositoryCustom {

	Optional<Usuario> findByEmail(String email);

	@Query("SELECT entity FROM Usuario entity "
			+ "INNER JOIN Biblioteca bib ON bib.administrador.id = entity.id "
			+ "LEFT JOIN UsuarioOperador op ON op.id = entity.id AND op.biblioteca.id = bib.id "
			+ "WHERE bib.id = :idBiblioteca")
	Page<Usuario> consultaOperadoresPorIdBiblioteca(@Param(value = "idBiblioteca") Integer idBiblioteca, Pageable page);

}
