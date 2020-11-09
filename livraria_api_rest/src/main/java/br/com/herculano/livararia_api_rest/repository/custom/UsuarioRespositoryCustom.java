package br.com.herculano.livararia_api_rest.repository.custom;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.herculano.livararia_api_rest.entity.Usuario;

public interface UsuarioRespositoryCustom { 
	
	public Optional<Usuario> consultaPorEmailComTipo(String email);
	
	Page<Usuario> consultaOperadoresPorIdBiblioteca(Integer idBiblioteca, Pageable page);
}
