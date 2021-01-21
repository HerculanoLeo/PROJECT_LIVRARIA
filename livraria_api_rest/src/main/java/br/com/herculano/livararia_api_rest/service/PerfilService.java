package br.com.herculano.livararia_api_rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.PerfilMessage;
import br.com.herculano.livararia_api_rest.controller.request.PerfilCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.PerfilConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.PermissaoConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.PerfilRepository;
import br.com.herculano.utilits.templates.ServiceTemplate;

@Service
public class PerfilService extends ServiceTemplate<Perfil, PerfilRepository, PerfilMessage>{

	@Autowired
	private PermissaoService permissaoService;
	
	@Autowired
	public PerfilService(PerfilRepository repository, @Qualifier("PerfilMessage") PerfilMessage message) {
		super(repository, message);
	}

	public Perfil cadastra(PerfilCadastroRequest request) {
		
		List<Permissao> permissoes = new ArrayList<Permissao>();

		validaPermissoes(request, permissoes);

		Perfil entity = new Perfil();

		entity.setNome(request.getNome());
		entity.setPermissoes(permissoes);

		save(entity);
		
		return entity;
	}

	public Perfil atualizar(Integer id, PerfilCadastroRequest request) {
		
		Perfil entity = super.consultaPorId(id);
		
		List<Permissao> permissoes = new ArrayList<Permissao>();
		validaPermissoes(request, permissoes);

		entity.setNome(request.getNome());
		entity.setPermissoes(permissoes);

		save(entity);

		return entity;
	}

	public List<Perfil> consultaGrupoUsuarios(List<Integer> idsGrupoUsuario) {
		return getRepository().findAllById(idsGrupoUsuario);
	}
	
	public Page<Perfil> consultaPorFiltro(PerfilConsultaRequest filter, Pageable page) {
		Page<Perfil> consultaPorFiltro = getRepository().consultaPorFiltro(filter, page);
		
		return consultaPorFiltro;
	}

	public void delete(Integer id) {
		Perfil entity = super.consultaPorId(id);
		
		super.delete(entity);
	}
	
	private void validaPermissoes(PerfilCadastroRequest request, List<Permissao> permissoes) {

		if (request.getPermissoes() != null) {
			for (PermissaoConsultaRequest permissao : request.getPermissoes()) {
				Permissao entity = permissaoService.consultaPorCodigo(permissao.getAuthority());

				permissoes.add(entity);
			}
		}
	}


}
