package br.com.herculano.livararia_api_rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;
import br.com.herculano.livararia_api_rest.repository.jpaRepository.GrupoUsuarioRepository;

@Service
public class GrupoUsuarioService extends ServiceTemplate<GrupoUsuario, GrupoUsuarioRepository>{

	@Autowired
	public GrupoUsuarioService(GrupoUsuarioRepository repository) {
		super(repository);
	}

	public List<GrupoUsuario> consultaGrupoUsuarios(List<Integer> idsGrupoUsuario) {
		return getRepository().findAllById(idsGrupoUsuario);
	}
	
	public Page<GrupoUsuario> consultaPorFiltro(GrupoUsuario filter, Pageable page) {
		return getRepository().consultaPorFiltro(filter, page);
	}

	public void delete(Integer id) {
		GrupoUsuario entity = super.consultaPorId(id);
		
		super.delete(entity);
	}
}
