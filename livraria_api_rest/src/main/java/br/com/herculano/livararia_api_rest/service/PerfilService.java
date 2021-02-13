package br.com.herculano.livararia_api_rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.constants.system_message.PerfilMessage;
import br.com.herculano.livararia_api_rest.controller.request.perfil.PerfilCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.perfil.PerfilConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.perfil.PerfilPermissaoConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.perfil.PerfilUpdateRequest;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.PerfilRepository;
import br.com.herculano.livararia_api_rest.repository.utils.ContextUtils;
import br.com.herculano.utilities.exceptions.DadosInvalidosException;
import br.com.herculano.utilities.exceptions.ResourceForbiddenException;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Service
public class PerfilService extends ServiceTemplate<Perfil, PerfilRepository, PerfilMessage> {

	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private UsuarioAdministradorService usuarioService;

	@Autowired
	public PerfilService(PerfilRepository repository, PerfilMessage message) {
		super(repository, message);
	}

	public Perfil cadastra(PerfilCadastroRequest entityRequest) {
		if (!isCadastroPermitido(entityRequest.getTipo())) {
			throw new ResourceForbiddenException(
					PerfilMessage.getCodigo(message.getNotAllowedTypeProfile(), new Object[] { TiposUsuariosEnum.getTipoUsuario(entityRequest.getTipo()) }));
		}

		List<Permissao> permissoes = validaPermissoes(entityRequest.getTipo(), entityRequest.getPermissoes());

		Perfil entity = new Perfil(entityRequest, permissoes);

		save(entity);

		return entity;
	}

	public Perfil cadastra(Integer idAdministrador, PerfilCadastroRequest request) {
		UsuarioAdministrador administrador = usuarioService.consultaPorId(idAdministrador);

		request.setAdministrador(administrador);

		Perfil entity = cadastra(request);

		return entity;
	}

	public Perfil atualizar(Integer id, PerfilUpdateRequest entityRequest) {
		Perfil entity = super.consultaPorId(id);

		if (!isCadastroPermitido(entityRequest.getTipo())) {
			throw new ResourceForbiddenException(
					PerfilMessage.getCodigo(message.getNotAllowedTypeProfile(), new Object[] { TiposUsuariosEnum.getTipoUsuario(entityRequest.getTipo()) }));
		}

		List<Permissao> permissoes = validaPermissoes(entityRequest.getTipo(), entityRequest.getPermissoes());

		entity.setNome(entityRequest.getNome());
		entity.setPermissoes(permissoes);
		entity.setTipo(entityRequest.getTipo());

		save(entity);

		return entity;
	}

	public Page<Perfil> consultaPorFiltro(PerfilConsultaRequest entityRequest, Pageable page) {
		Page<Perfil> consultaPorFiltro = getRepository().consultaPorFiltro(entityRequest, page);

		return consultaPorFiltro;
	}

	public void delete(Integer id) {
		Perfil entity = super.consultaPorId(id);

		super.delete(entity);
	}

	private List<Permissao> validaPermissoes(String tipoPerfil, List<PerfilPermissaoConsultaRequest> permissoes) {
		List<Permissao> entities = new ArrayList<>();
		if (null != permissoes && !permissoes.isEmpty()) {
			for (PerfilPermissaoConsultaRequest permissao : permissoes) {
				Permissao entity = permissaoService.consultaPorCodigo(permissao.getCodigo());

				if (tipoPerfil.equals(TiposUsuariosEnum.ROOT.getValor())) {

					if ((tipoPerfil.equals(TiposUsuariosEnum.ADMINISTRADOR.getValor()) || tipoPerfil.equals(TiposUsuariosEnum.OPERADOR.getValor()))
							&& !entity.isBiblioteca()) {
						throw new DadosInvalidosException(PerfilMessage.getCodigo(message.getNotAllowedTypeProfile(),
								new Object[] { TiposUsuariosEnum.getTipoUsuario(tipoPerfil), TiposUsuariosEnum.getTipoUsuario(entity.getTipo()) }));
					}

					if ((tipoPerfil.equals(TiposUsuariosEnum.CLIENTE.getValor()) && !entity.isCliente())) {
						throw new DadosInvalidosException(PerfilMessage.getCodigo(message.getNotAllowedTypeProfileTypePermission(),
								new Object[] { TiposUsuariosEnum.getTipoUsuario(tipoPerfil), TiposUsuariosEnum.getTipoUsuario(entity.getTipo()) }));
					}

				}

				entities.add(entity);
			}
		}

		return entities;
	}

	private boolean isCadastroPermitido(String tipoPerfil) {
		// Valid if exist type profile
		TiposUsuariosEnum.getTipoUsuario(tipoPerfil);

		Usuario usuario = ContextUtils.getUsuarioAutenticado();

		if (usuario.isRoot()) {
			return true;
		}

		if (usuario.isBiblioteca() && tipoPerfil.equals(TiposUsuariosEnum.OPERADOR.getValor())) {
			return true;
		}

		return false;
	}

}
