package br.com.herculano.livararia_api_rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.GrupoUsuarioMessage;
import br.com.herculano.livararia_api_rest.controller.request.GrupoUsuarioCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.GrupoUsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.PermissaoConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;
import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.repository.jpaRepository.GrupoUsuarioRepository;

@Service
public class GrupoUsuarioService extends ServiceTemplate<GrupoUsuario, GrupoUsuarioRepository, GrupoUsuarioMessage>{

	@Autowired
	private PermissaoService permissaoService;
	
	@Autowired
	public GrupoUsuarioService(GrupoUsuarioRepository repository, @Qualifier("GrupoUsuarioMessage") GrupoUsuarioMessage message) {
		super(repository, message);
	}

	public GrupoUsuario cadastra(GrupoUsuarioCadastroRequest request) {
		
		List<Permissao> permissoes = new ArrayList<Permissao>();

		validaPermissoes(request, permissoes);

		GrupoUsuario entity = new GrupoUsuario();

		entity.setNome(request.getNome());
		entity.setPermissoes(permissoes);

		save(entity);
		
		return entity;
	}

	public GrupoUsuario atualizar(Integer id, GrupoUsuarioCadastroRequest request) {
		
		GrupoUsuario entity = super.consultaPorId(id);
		
		List<Permissao> permissoes = new ArrayList<Permissao>();
		validaPermissoes(request, permissoes);

		entity.setNome(request.getNome());
		entity.setPermissoes(permissoes);

		save(entity);

		return entity;
	}

	public List<GrupoUsuario> consultaGrupoUsuarios(List<Integer> idsGrupoUsuario) {
		return getRepository().findAllById(idsGrupoUsuario);
	}
	
	public Page<GrupoUsuario> consultaPorFiltro(GrupoUsuarioConsultaRequest filter, Pageable page) {
		return getRepository().consultaPorFiltro(filter, page);
	}

	public void delete(Integer id) {
		GrupoUsuario entity = super.consultaPorId(id);
		
		super.delete(entity);
	}
	
	private void validaPermissoes(GrupoUsuarioCadastroRequest request, List<Permissao> permissoes) {

		if (request.getPermissoes() != null) {
			for (PermissaoConsultaRequest permissao : request.getPermissoes()) {
				Permissao entity = permissaoService.consultaPorCodigo(permissao.getAuthority());

				permissoes.add(entity);
			}
		}
	}


}
