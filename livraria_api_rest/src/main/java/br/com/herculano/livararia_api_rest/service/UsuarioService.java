package br.com.herculano.livararia_api_rest.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.constants.system_message.UsuarioMessage;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioClienteCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioRootCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioUpdateRequest;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.entity.UsuarioCliente;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.UsuarioRepository;
import br.com.herculano.utilits.exceptions.ConfirmPasswordException;
import br.com.herculano.utilits.exceptions.DadosInvalidosException;
import br.com.herculano.utilits.exceptions.EmptyPerfilException;
import br.com.herculano.utilits.templates.MessageTemplate;
import br.com.herculano.utilits.templates.ServiceTemplate;

@Service
public class UsuarioService extends ServiceTemplate<Usuario, UsuarioRepository, UsuarioMessage> implements UserDetailsService {

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	public UsuarioService(UsuarioRepository repository, @Qualifier("UsuarioMessage") UsuarioMessage message) {
		super(repository, message);
	}

	@Override
	public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optionalUsuario = getRepository().findByEmail(username);

		if (!optionalUsuario.isPresent()) {
			throw new UsernameNotFoundException("User " + username + " not found.");
		}

		Usuario entity = optionalUsuario.get();
		entity.setPermissoes(permissaoService.consultaPorIdPerfil(entity.getPerfil().getId()));

		return entity;
	}

	public Usuario cadastraCliente(UsuarioClienteCadastroRequest entityRequest) {
		if (entityRequest.getSenha().equals(entityRequest.getConfirmeSenha())) {
			Optional<Usuario> optional = getRepository().findByEmail(entityRequest.getEmail());

			if (optional.isPresent()) {
				throw new DadosInvalidosException("E-mail aready exist.");
			}

			Perfil perfil = perfilService.consultaPorId(3);

			if (!perfil.getTipo().equals(TiposUsuariosEnum.CLIENTE.getValor())) {
				throw new DadosInvalidosException("Perfil is not compatible.");
			}

			entityRequest.setPerfil(perfil);
			entityRequest.setTipo(TiposUsuariosEnum.CLIENTE.getValor());

			UsuarioCliente entity = new UsuarioCliente(entityRequest);

			super.save(entity);

			return entity;
		} else {
			throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordNotMatch(), null));
		}
	}

	public Usuario cadastraRoot(UsuarioRootCadastroRequest entityRequest) {
		Optional<Usuario> optional = getRepository().findByEmail(entityRequest.getEmail());

		if (optional.isPresent()) {
			throw new DadosInvalidosException("E-mail aready exist.");
		}

		Perfil perfil = perfilService.consultaPorId(1);

		if (!perfil.getTipo().equals(TiposUsuariosEnum.ROOT.getValor())) {
			throw new DadosInvalidosException("Profile is not compatible for User Type.");
		}
		entityRequest.setTipo(TiposUsuariosEnum.ROOT.getValor());

		String password = RandomStringUtils.randomAlphanumeric(6);
		System.out.println("Password: " + password);
		entityRequest.setSenha(password);
		entityRequest.setPerfil(perfil);

		Usuario entity = new Usuario(entityRequest);

		super.save(entity);

		return entity;
	}

	public Usuario atualiza(Integer id, UsuarioUpdateRequest request) {
		Usuario entity = super.consultaPorId(id);

		entity.setNome(request.getNome());

		if (null != request.getIdPerfil() && !entity.getPerfil().getId().equals(request.getIdPerfil())) {
			Perfil perfil = perfilService.consultaPorId(request.getIdPerfil());

			if (perfil.getTipo().equals(entity.getTipoUsuario())) {
				entity.setPerfil(perfil);
			} else {
				throw new DadosInvalidosException("Profile is not compatible.");
			}
		}

		super.save(entity);

		return entity;
	}

	public Usuario consultaPorEmail(String email) {
		Optional<Usuario> optionalUsuario = getRepository().findByEmail(email);

		if (!optionalUsuario.isPresent()) {
			Object[] args = { email };

			throw new EntityNotFoundException(MessageTemplate.getCodigo(getMessage().getEmailNotFound(), args));
		}

		return optionalUsuario.get();
	}

	public Usuario cadastraGrupoUsuarioPorIds(Integer idUsuario, Integer idGrupoUsuario) {

		if (null == idGrupoUsuario) {
			throw new EmptyPerfilException(MessageTemplate.getCodigo(getMessage().getEmptyGroupUser(), null));
		}

		Usuario entity = super.consultaPorId(idUsuario);

		Perfil perfil = perfilService.consultaPorId(idGrupoUsuario);

		entity.setPerfil(perfil);

		super.save(entity);

		return entity;
	}

	public Page<Usuario> consultaUsuarioPorIdBiblioteca(Integer idBiblioteca, Pageable page) {
		return getRepository().consultaOperadoresPorIdBiblioteca(idBiblioteca, page);
	}

	public Page<Usuario> consultaPorFiltro(UsuarioConsultaRequest entityRequest, Pageable page) {
		return getRepository().consultaPorFiltro(entityRequest, page);
	}
}
