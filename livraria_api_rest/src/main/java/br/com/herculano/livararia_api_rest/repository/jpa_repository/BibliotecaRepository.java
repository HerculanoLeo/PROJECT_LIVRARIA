package br.com.herculano.livararia_api_rest.repository.jpa_repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.repository.custom.BibliotecaRespositoryCustom;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Integer>, BibliotecaRespositoryCustom {

	@Query("SELECT entity FROM Biblioteca entity"
			+ " LEFT JOIN UsuarioAdministrador a ON a.id = entity.administrador.id"
			+ " LEFT JOIN UsuarioOperador o ON o.biblioteca.id = entity.id"
			+ " WHERE (a.id = :a OR o.id = :a)")
	List<Biblioteca> consultaPorIdUsuario(@Param(value = "a") Integer id);

}
