package br.com.herculano.livararia_api_rest.auth.token;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.UsuarioRepository;
import br.com.herculano.livararia_api_rest.service.BibliotecaService;
import br.com.herculano.livararia_api_rest.service.PermissaoService;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BibliotecaService bibliotecaService;
	
	@Autowired
	private PermissaoService permissaoService;

	@Override
	public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optionalUsuario = usuarioRepository.consultaPorEmailComTipo(username);
		
		if (!optionalUsuario.isPresent()) {
			throw new UsernameNotFoundException("User " + username + " not found.");
		}
		
		Usuario entity = optionalUsuario.get();
		entity.setPermissoes(permissaoService.consultaPorIdUsuario(entity.getId()));
		
		if(entity.getTipo().equals("bib") || entity.getTipo().equals("op")) {
			entity.setBiblioteca(bibliotecaService.consultaPorUsuarioId(entity.getId()));
		}
		
		return entity;
	}
}
