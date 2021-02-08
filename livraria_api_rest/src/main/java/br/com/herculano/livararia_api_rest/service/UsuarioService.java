package br.com.herculano.livararia_api_rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.ConfiguracaoEnum;
import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.constants.system_message.UsuarioMessage;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.AdministradorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.AdministradorUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.OperadorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.OperadorUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioAtualizaPerfilRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioClienteCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioClienteUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioRootCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioRootUpdateRequest;
import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.entity.Configuracao;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import br.com.herculano.livararia_api_rest.entity.UsuarioCliente;
import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.UsuarioRepository;
import br.com.herculano.utilities.exceptions.ConfirmPasswordException;
import br.com.herculano.utilities.exceptions.DadosInvalidosException;
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
	private UsuarioAdministradorService administradorService;

	@Autowired
	private UsuarioOperadorService operadorService;

	@Autowired
	private UsuarioClienteService clienteService;

	@Autowired
	private BibliotecaService bibliotecaService;

	@Autowired
	public UsuarioService(UsuarioRepository repository, UsuarioMessage message) {
		super(repository, message);
	}

	@Override
	public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optional = getRepository().findByEmail(username);

		if (!optional.isPresent()) {
			Object[] args = new Object[] { username };

			throw new UsernameNotFoundException(MessageTemplate.getCodigo(message.getAuthenticationNotFound(), args));
		}

		Usuario entity = optional.get();
		entity.setPermissoes(permissaoService.consultaPorIdPerfil(entity.getPerfil().getId()));
		
		if(entity.isBiblioteca()) {
			List<Biblioteca> bibliotecas = bibliotecaService.consultaPorIdUsuario(entity.getId());

			List<Integer> ids = new ArrayList<Integer>();

			for (Biblioteca biblioteca : bibliotecas) {
				if (null == entity.getIdUsuarioAdministrador()) {
					entity.setIdUsuarioAdministrador(biblioteca.getAdministrador().getId());
				}

				ids.add(biblioteca.getId());
			}

			entity.setIdsBiblioteca(ids);
		}

		return entity;
	}

	public Page<Usuario> consultaPorFiltro(UsuarioConsultaRequest entityRequest, Pageable page) {
		return getRepository().consultaPorFiltro(entityRequest, page);
	}

	public Usuario cadastraRoot(UsuarioRootCadastroRequest entityRequest) {
		validaEmailDisponivel(entityRequest.getEmail());

		Perfil perfil = perfilService.consultaPorId(entityRequest.getIdPerfil());

		if (!perfil.getTipo().equals(TiposUsuariosEnum.ROOT.getValor())) {
			Object[] args = new Object[] { TiposUsuariosEnum.ROOT.getValor() };

			throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getProfileNotCompatible(), args));
		}

		String password = RandomStringUtils.randomAlphanumeric(8);
		System.out.println("Password: " + password);

		entityRequest.setSenha(password);
		entityRequest.setPerfil(perfil);

		Usuario entity = new Usuario(entityRequest);

		super.save(entity);

		return entity;
	}

	public Usuario atualizaRoot(Integer idUsuario, UsuarioRootUpdateRequest entityRequest) {
		Usuario entity = super.consultaPorId(idUsuario);

		entity.setNome(entityRequest.getNome());
		entity.setIdioma(entityRequest.getIdioma());

		if (!entity.getEmail().equals(entityRequest.getEmail())) {
			validaEmailDisponivel(entityRequest.getEmail());

			entity.setEmail(entityRequest.getEmail());
		}

		super.save(entity);

		return entity;
	}

	public UsuarioAdministrador cadastroAdministrador(AdministradorCadastroRequest entityRequest) {
		if (entityRequest.getSenha().equals(entityRequest.getConfirmeSenha())) {
			validaEmailDisponivel(entityRequest.getEmail());

			Configuracao configuracaoPerfilPadrao = configuracaService.consultaPorId(ConfiguracaoEnum.PERFIL_ADMINISTADOR_PADRAO.getValor());

			Perfil perfil = perfilService.consultaPorId(Integer.valueOf(configuracaoPerfilPadrao.getValor()));

			if (!perfil.getTipo().equals(TiposUsuariosEnum.ADMINISTRADOR.getValor())) {
				Object[] args = new Object[] { TiposUsuariosEnum.ADMINISTRADOR.getValor() };

				throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getProfileNotCompatible(), args));
			}

			entityRequest.setPerfil(perfil);

			UsuarioAdministrador entity = new UsuarioAdministrador(entityRequest);

			super.save(entity);

			return entity;
		} else {
			throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordNotMatch(), null));
		}
	}

	public UsuarioAdministrador atualizarAdministrador(Integer idAdministrador, AdministradorUpdateRequest entityRequest) {
		UsuarioAdministrador entity = administradorService.consultaPorId(idAdministrador);

		if (!entityRequest.getEmail().equals(entity.getEmail())) {
			validaEmailDisponivel(entityRequest.getEmail());

			entity.setEmail(entityRequest.getEmail());
		}

		entity.setNome(entityRequest.getNome());
		entity.setDocumento(entityRequest.getDocumento());

		super.save(entity);

		return entity;
	}

	public UsuarioOperador cadastraOperador(OperadorCadastroRequest entityRequest) {
		validaEmailDisponivel(entityRequest.getEmail());

		Perfil perfil = perfilService.consultaPorId(entityRequest.getIdPerfil());

		if (!perfil.getTipo().equals(TiposUsuariosEnum.OPERADOR.getValor())) {
			Object[] args = new Object[] { TiposUsuariosEnum.OPERADOR.getValor() };

			throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getProfileNotCompatible(), args));
		}

		String password = RandomStringUtils.randomAlphanumeric(8);
		System.out.println("Password: " + password);

		entityRequest.setPerfil(perfil);
		entityRequest.setSenha(password);

		UsuarioOperador entity = new UsuarioOperador(entityRequest);

		super.save(entity);

		return entity;
	}

	public UsuarioOperador atualizarOperador(Integer idOperador, OperadorUpdateRequest entityRequest) {
		UsuarioOperador entity = operadorService.consultaPorId(idOperador);

		validaEmailDisponivel(entityRequest.getEmail());

		if (!entity.getEmail().equals(entityRequest.getEmail())) {
			validaEmailDisponivel(entityRequest.getEmail());

			entity.setEmail(entityRequest.getEmail());
		}

		super.save(entity);

		return entity;
	}

	public UsuarioCliente cadastraCliente(UsuarioClienteCadastroRequest entityRequest) {
		if (entityRequest.getSenha().equals(entityRequest.getConfirmeSenha())) {
			validaEmailDisponivel(entityRequest.getEmail());

			Configuracao configuracaoPerfilPadrao = configuracaService.consultaPorId(ConfiguracaoEnum.PERFIL_CLIENTE_PADRAO.getValor());

			Perfil perfil = perfilService.consultaPorId(Integer.valueOf(configuracaoPerfilPadrao.getValor()));

			if (!perfil.getTipo().equals(TiposUsuariosEnum.CLIENTE.getValor())) {
				Object[] args = new Object[] { TiposUsuariosEnum.CLIENTE.getValor() };

				throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getProfileNotCompatible(), args));
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

	public UsuarioCliente atualizaCliente(Integer idUsuario, UsuarioClienteUpdateRequest entityRequest) {
		UsuarioCliente entity = clienteService.consultaPorId(idUsuario);

		entity.setNome(entityRequest.getNome());
		entity.setDocumento(entityRequest.getDocumento());
		entity.setIdioma(entityRequest.getIdioma());

		if (!entity.getEmail().equals(entityRequest.getEmail())) {
			validaEmailDisponivel(entityRequest.getEmail());

			entity.setEmail(entityRequest.getEmail());
		}

		super.save(entity);

		return entity;
	}

	public Usuario atualizaPerfil(UsuarioAtualizaPerfilRequest entityRequest) {
		Usuario entity = consultaPorId(entityRequest.getIdUsuario());

		Perfil perfil = perfilService.consultaPorId(entityRequest.getIdPerfil());

		if (!entity.getTipoUsuario().equals(perfil.getTipo())) {
			Object[] args = new Object[] { TiposUsuariosEnum.getTipoUsuario(entity.getTipoUsuario()) };

			throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getProfileNotCompatible(), args));
		}

		if (entity.isBiblioteca() && null != perfil.getAdministrador()) {
			if (entity instanceof UsuarioAdministrador) {
				if (!entity.getId().equals(perfil.getAdministrador().getId())) {
					throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getProfileNotBelongAdministrator(), null));
				}
			}

			if (entity instanceof UsuarioOperador) {
				if (!((UsuarioOperador) entity).getBiblioteca().getAdministrador().getId().equals(perfil.getAdministrador().getId())) {
					throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getProfileNotBelongAdministrator(), null));
				}
			}
		}

		entity.setPerfil(perfil);

		entity = save(entity);

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

	private void validaEmailDisponivel(String email) {
		Optional<Usuario> optional = getRepository().findByEmail(email);

		if (optional.isPresent()) {
			throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getEmailAlreadyExist(), null));
		}
	}

}
