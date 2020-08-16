package br.com.herculano.livararia_api_rest.auth.token;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.repository.jpaRepository.UsuarioRepository;
import br.com.herculano.livararia_api_rest.service.PermissaoService;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PermissaoService permissaoService;

	@Override
	public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(username);
		
		if (!optionalUsuario.isPresent()) {
			throw new UsernameNotFoundException("Usuario " + username + " não foi encontrado");
		}
		
		Usuario entity = optionalUsuario.get();
		entity.setPermissoes(permissaoService.consultaPorIdUsuario(entity.getId()));
		
		return entity;
	}
}
