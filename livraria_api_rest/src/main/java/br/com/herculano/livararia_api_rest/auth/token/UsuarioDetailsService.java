package br.com.herculano.livararia_api_rest.auth.token;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.UsuarioRepository;
import br.com.herculano.livararia_api_rest.service.PermissaoService;
import br.com.herculano.utilities.templates.CommonMessageTemplate;
import br.com.herculano.utilities.templates.MessageTemplate;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PermissaoService permissaoService;
	
	@Autowired
	private CommonMessageTemplate message;

	@Override
	public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(username);
		
		if (!optionalUsuario.isPresent()) {
			Object[] args = new Object[] { username };
			
			throw new UsernameNotFoundException(MessageTemplate.getCodigo(message.getUserNotFound(), args));
		}
		
		Usuario entity = optionalUsuario.get();
		entity.setPermissoes(permissaoService.consultaPorIdPerfil(entity.getPerfil().getId()));
		
		return entity;
	}
}
