package br.com.herculano.livararia_api_rest.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

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
import br.com.herculano.livararia_api_rest.controller.request.AdministradorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.AdministradorUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.OperadorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.OperadorUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioClienteCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioClienteUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioRootCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioRootUpdateRequest;
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

	public Usuario atualizaRoot(UsuarioRootUpdateRequest entityRequest) {
		Usuario entity = super.consultaPorId(entityRequest.getIdUsuario());

		entity.setNome(entityRequest.getNome());

		if (!entity.getEmail().equals(entityRequest.getEmail())) {

			validaEmailDisponivel(entityRequest.getEmail());

			entity.setEmail(entityRequest.getEmail());
		}

		if (null != entityRequest.getIdPerfil() && !entity.getPerfil().getId().equals(entityRequest.getIdPerfil())) {
			
			Perfil perfil = perfilService.consultaPorId(entityRequest.getIdPerfil());

			if (!perfil.getTipo().equals(TiposUsuariosEnum.ADMINISTRADOR.getValor())) {
				Object[] args = new Object[] { TiposUsuariosEnum.ADMINISTRADOR.getValor() };

				throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getProfileNotCompatible(), args));
			}

			entity.setPerfil(perfil);
		}

		super.save(entity);

		return entity;
	}

	public UsuarioAdministrador cadastroAdministrador(AdministradorCadastroRequest entityRequest) {
		if (entityRequest.getSenha().equals(entityRequest.getConfirmeSenha())) {
			validaEmailDisponivel(entityRequest.getEmail());

			Configuracao configuracaoPerfilPadrao = configuracaService
					.consultaPorId(ConfiguracaoEnum.PERFIL_ADMINISTADOR_PADRAO.getValor());

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

	public UsuarioAdministrador atualizarAdministrador(@Valid AdministradorUpdateRequest entityRequest) {
		UsuarioAdministrador entity = administradorService.consultaPorId(entityRequest.getIdAdministrador());

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

	public UsuarioOperador atualizarOperador(@Valid OperadorUpdateRequest entityRequest) {
		UsuarioOperador entity = operadorService.consultaPorId(entityRequest.getIdOperador());

		validaEmailDisponivel(entityRequest.getEmail());

		if (!entity.getEmail().equals(entityRequest.getEmail())) {
			validaEmailDisponivel(entityRequest.getEmail());

			entity.setEmail(entityRequest.getEmail());
		}

		if (null != entityRequest.getIdPerfil() && !entity.getPerfil().getId().equals(entityRequest.getIdPerfil())) {
			
			Perfil perfil = perfilService.consultaPorId(entityRequest.getIdPerfil());

			if (!perfil.getTipo().equals(TiposUsuariosEnum.OPERADOR.getValor())) {
				Object[] args = new Object[] { TiposUsuariosEnum.OPERADOR.getValor() };

				throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getProfileNotCompatible(), args));
			}

			entity.setPerfil(perfil);
		}

		super.save(entity);

		return entity;
	}

	public UsuarioCliente cadastraCliente(UsuarioClienteCadastroRequest entityRequest) {
		if (entityRequest.getSenha().equals(entityRequest.getConfirmeSenha())) {
			validaEmailDisponivel(entityRequest.getEmail());

			Configuracao configuracaoPerfilPadrao = configuracaService
					.consultaPorId(ConfiguracaoEnum.PERFIL_CLIENTE_PADRAO.getValor());

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

	public UsuarioCliente atualizaCliente(UsuarioClienteUpdateRequest entityRequest) {
		UsuarioCliente entity = clienteService.consultaPorId(entityRequest.getIdUsuario());

		entity.setNome(entityRequest.getNome());
		entity.setDocumento(entityRequest.getDocumento());

		if (!entity.getEmail().equals(entityRequest.getEmail())) {
			validaEmailDisponivel(entityRequest.getEmail());

			entity.setEmail(entityRequest.getEmail());
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

	private void validaEmailDisponivel(String email) {
		Optional<Usuario> optional = getRepository().findByEmail(email);

		if (optional.isPresent()) {
			throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getEmailAlreadyExist(), null));
		}
	}

}
