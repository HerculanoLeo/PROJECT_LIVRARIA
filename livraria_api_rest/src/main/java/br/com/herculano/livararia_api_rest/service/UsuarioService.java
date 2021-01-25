package br.com.herculano.livararia_api_rest.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.ConfiguracaoEnum;
import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.constants.system_message.UsuarioMessage;
import br.com.herculano.livararia_api_rest.controller.request.AdministradorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.AdministradorUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaOperadorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioClienteCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioRootCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioUpdateRequest;
import br.com.herculano.livararia_api_rest.entity.Configuracao;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import br.com.herculano.livararia_api_rest.entity.UsuarioCliente;
import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.UsuarioRepository;
import br.com.herculano.utilities.exceptions.ConfirmPasswordException;
import br.com.herculano.utilities.exceptions.DadosInvalidosException;
import br.com.herculano.utilities.exceptions.EmptyPerfilException;
import br.com.herculano.utilities.templates.MessageTemplate;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Service
public class UsuarioService extends ServiceTemplate<Usuario, UsuarioRepository, UsuarioMessage> implements UserDetailsService {

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private ConfiguracaoService configuracaService;

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

	public Page<Usuario> consultaPorFiltro(UsuarioConsultaRequest entityRequest, Pageable page) {
		return getRepository().consultaPorFiltro(entityRequest, page);
	}

	public Usuario cadastraRoot(UsuarioRootCadastroRequest entityRequest) {
		Optional<Usuario> optional = getRepository().findByEmail(entityRequest.getEmail());

		if (optional.isPresent()) {
			throw new DadosInvalidosException("E-mail aready exist.");
		}

		Perfil perfil = perfilService.consultaPorId(entityRequest.getIdPerfil());

		if (!perfil.getTipo().equals(TiposUsuariosEnum.ROOT.getValor())) {
			throw new DadosInvalidosException("Profile is not compatible for User Type.");
		}

		String password = RandomStringUtils.randomAlphanumeric(8);
		System.out.println("Password: " + password);
		entityRequest.setSenha(password);
		entityRequest.setPerfil(perfil);

		Usuario entity = new Usuario(entityRequest);

		super.save(entity);

		return entity;
	}

	public UsuarioAdministrador cadatroAdministrador(AdministradorCadastroRequest entityRequest) {
		if (entityRequest.getSenha().equals(entityRequest.getConfirmeSenha())) {
			Optional<Usuario> optional = getRepository().findByEmail(entityRequest.getEmail());

			if (optional.isPresent()) {
				throw new DadosInvalidosException("E-mail aready exist.");
			}

			Configuracao configuracaoPerfilPadrao = configuracaService
					.consultaPorId(ConfiguracaoEnum.PERFIL_ADMINISTADOR_PADRAO.getValor());

			Perfil perfil = perfilService.consultaPorId(Integer.valueOf(configuracaoPerfilPadrao.getValor()));

			if (!perfil.getTipo().equals(TiposUsuariosEnum.ADMINISTRADOR.getValor())) {
				throw new DadosInvalidosException("Perfil is not compatible.");
			}

			entityRequest.setPerfil(perfil);

			UsuarioAdministrador entity = new UsuarioAdministrador(entityRequest);

			super.save(entity);

			return entity;
		} else {
			throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordNotMatch(), null));
		}
	}

	public UsuarioAdministrador atualizarAdministrador(@Valid AdministradorUpdateRequest entityRequest) {
		Usuario usuario = consultaPorId(entityRequest.getIdAdministrador());
		
		if (usuario instanceof UsuarioAdministrador) {

			UsuarioAdministrador entity = (UsuarioAdministrador) usuario;
			
			if (!entityRequest.getEmail().equals(entity.getEmail())) {
				Optional<Usuario> optional = getRepository().findByEmail(entityRequest.getEmail());

				if (optional.isPresent()) {
					throw new DadosInvalidosException("E-mail aready exist.");
				}

				entity.setEmail(entityRequest.getEmail());
			}

			entity.setNome(entityRequest.getNome());
			entity.setDocumento(entityRequest.getDocumento());

			super.save(entity);

			return entity;
		} else {
			throw new DadosInvalidosException("User is not a Administrator");
		}
	}

	public UsuarioOperador cadastraOperador(BibliotecaOperadorCadastroRequest entityRequest) {
		Optional<Usuario> optional = getRepository().findByEmail(entityRequest.getEmail());

		if (optional.isPresent()) {
			throw new DadosInvalidosException("E-mail aready exist.");
		}

		Perfil perfil = perfilService.consultaPorId(entityRequest.getIdPerfil());

		if (!perfil.getTipo().equals(TiposUsuariosEnum.OPERADOR.getValor())) {
			throw new DadosInvalidosException("Perfil is not compatible.");
		}

		String password = RandomStringUtils.randomAlphanumeric(8);
		System.out.println("Password: " + password);

		entityRequest.setPerfil(perfil);
		entityRequest.setSenha(password);

		UsuarioOperador entity = new UsuarioOperador(entityRequest);

		super.save(entity);

		return entity;
	}

	public UsuarioCliente cadastraCliente(UsuarioClienteCadastroRequest entityRequest) {
		if (entityRequest.getSenha().equals(entityRequest.getConfirmeSenha())) {
			Optional<Usuario> optional = getRepository().findByEmail(entityRequest.getEmail());

			if (optional.isPresent()) {
				throw new DadosInvalidosException("E-mail aready exist.");
			}

			Configuracao configuracaoPerfilPadrao = configuracaService
					.consultaPorId(ConfiguracaoEnum.PERFIL_CLIENTE_PADRAO.getValor());

			Perfil perfil = perfilService.consultaPorId(Integer.valueOf(configuracaoPerfilPadrao.getValor()));

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

	public Usuario cadastraPerfilPorId(Integer idUsuario, Integer idPerfil) {
		if (null == idPerfil) {
			throw new EmptyPerfilException(MessageTemplate.getCodigo(getMessage().getEmptyGroupUser(), null));
		}

		Usuario entity = super.consultaPorId(idUsuario);

		Perfil perfil = perfilService.consultaPorId(idPerfil);

		entity.setPerfil(perfil);

		super.save(entity);

		return entity;
	}

}
