package br.com.herculano.livararia_api_rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.constants.system_message.PerfilMessage;
import br.com.herculano.livararia_api_rest.controller.request.PerfilCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.PerfilConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.PermissaoConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.entity.Permissao;
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
	public PerfilService(PerfilRepository repository, PerfilMessage message) {
		super(repository, message);
	}

	public Perfil cadastra(PerfilCadastroRequest entityRequest) {
		List<Permissao> permissoes = new ArrayList<Permissao>();

		if (!isCadastroPermitido(entityRequest)) {
			throw new ResourceForbiddenException(PerfilMessage.getCodigo(message.getNotAllowedTypeProfile(),
					new Object[] { TiposUsuariosEnum.getTipoUsuario(entityRequest.getTipo()) }));
		}

		validaPermissoes(entityRequest, permissoes);

		Perfil entity = new Perfil();

		entity.setNome(entityRequest.getNome());
		entity.setPermissoes(permissoes);
		entity.setTipo(entityRequest.getTipo());

		save(entity);

		return entity;
	}

	public Perfil atualizar(Integer id, PerfilCadastroRequest entityRequest) {
		Perfil entity = super.consultaPorId(id);

		List<Permissao> permissoes = new ArrayList<Permissao>();

		if (!isCadastroPermitido(entityRequest)) {
			throw new ResourceForbiddenException(PerfilMessage.getCodigo(message.getNotAllowedTypeProfile(),
					new Object[] { TiposUsuariosEnum.getTipoUsuario(entityRequest.getTipo()) }));
		}

		validaPermissoes(entityRequest, permissoes);

		entity.setNome(entityRequest.getNome());
		entity.setPermissoes(permissoes);
		entity.setTipo(entityRequest.getTipo());

		save(entity);

		return entity;
	}

	public Page<Perfil> consultaPorFiltro(PerfilConsultaRequest filter, Pageable page) {
		Page<Perfil> consultaPorFiltro = getRepository().consultaPorFiltro(filter, page);

		return consultaPorFiltro;
	}

	public void delete(Integer id) {
		Perfil entity = super.consultaPorId(id);

		super.delete(entity);
	}

	private void validaPermissoes(PerfilCadastroRequest entityRequest, List<Permissao> permissoes) {
		if (entityRequest.getPermissoes() != null) {
			for (PermissaoConsultaRequest permissao : entityRequest.getPermissoes()) {
				Permissao entity = permissaoService.consultaPorCodigo(permissao.getAuthority());

				if ((entityRequest.getTipo().equals(TiposUsuariosEnum.ADMINISTRADOR.getValor())
						|| entityRequest.getTipo().equals(TiposUsuariosEnum.OPERADOR.getValor()) && !entity.isBiblioteca())) {
					throw new DadosInvalidosException(PerfilMessage.getCodigo(message.getNotAllowedTypeProfile(),
							new Object[] { TiposUsuariosEnum.getTipoUsuario(entityRequest.getTipo()),  TiposUsuariosEnum.getTipoUsuario(entity.getTipo())}));
				}

				if ((entityRequest.getTipo().equals(TiposUsuariosEnum.CLIENTE.getValor()) && !entity.isCliente())) {
					throw new DadosInvalidosException(PerfilMessage.getCodigo(message.getNotAllowedTypeProfileTypePermission(),
							new Object[] { TiposUsuariosEnum.getTipoUsuario(entityRequest.getTipo()),  TiposUsuariosEnum.getTipoUsuario(entity.getTipo())}));
				}

				permissoes.add(entity);
			}
		}
	}

	private boolean isCadastroPermitido(PerfilCadastroRequest entityRequest) {
		String tipoUsuario = ContextUtils.getUsuarioAutenticado().getTipoUsuario();

		if (TiposUsuariosEnum.ROOT.getValor().equals(tipoUsuario)) {
			return true;
		}

		if ((TiposUsuariosEnum.ADMINISTRADOR.getValor().equals(tipoUsuario)
				|| TiposUsuariosEnum.OPERADOR.getValor().equals(tipoUsuario))
				&& entityRequest.getTipo().equals(TiposUsuariosEnum.OPERADOR.getValor())) {
			return true;
		}

		return false;
	}

}
