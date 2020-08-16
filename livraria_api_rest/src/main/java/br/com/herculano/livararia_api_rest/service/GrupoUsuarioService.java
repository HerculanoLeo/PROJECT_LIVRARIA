package br.com.herculano.livararia_api_rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	
	
}
