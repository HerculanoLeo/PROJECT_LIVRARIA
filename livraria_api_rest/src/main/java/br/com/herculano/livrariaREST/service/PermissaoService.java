package br.com.herculano.livrariaREST.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.herculano.livrariaREST.entity.GrupoUsuario;
import br.com.herculano.livrariaREST.entity.Permissao;
import br.com.herculano.livrariaREST.repository.jpaRepository.PermissaoRepository;

@Service
public class PermissaoService extends ServiceTemplate<Permissao, PermissaoRepository>{

	@Autowired
	public PermissaoService(PermissaoRepository repository) {
		super(repository);
	}

	public List<Permissao> consultaPorIdUsuario(Integer idCliente) {
		return getRepository().consultaPorIdUsuario(idCliente);
	}

	public List<GrupoUsuario> consultaGrupoUsuarios(List<Integer> idsGrupoUsuario) {
		return null;
	}

	public Optional<Permissao> consultaPorCodigo(String codigo) {
		return getRepository().findByCodigo(codigo);
	}

}
