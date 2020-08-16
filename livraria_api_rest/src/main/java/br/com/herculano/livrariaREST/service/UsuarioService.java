package br.com.herculano.livrariaREST.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.herculano.livrariaREST.entity.Usuario;
import br.com.herculano.livrariaREST.repository.jpaRepository.UsuarioRepository;

@Service
public class UsuarioService extends ServiceTemplate<Usuario, UsuarioRepository>{

	@Autowired
	public UsuarioService(UsuarioRepository repository) {
		super(repository);
	}

	public Optional<Usuario> consultaPorEmail(String email) {
		return getRepository().findByEmail(email);
	}

}
