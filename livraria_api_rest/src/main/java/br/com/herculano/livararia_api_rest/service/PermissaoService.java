package br.com.herculano.livararia_api_rest.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.repository.jpaRepository.PermissaoRepository;

@Service
public class PermissaoService extends ServiceTemplate<Permissao, PermissaoRepository>{

	@Autowired
	public PermissaoService(PermissaoRepository repository) {
		super(repository);
	}

	public List<Permissao> consultaPorIdUsuario(Integer idCliente) {
		return getRepository().consultaPorIdUsuario(idCliente);
	}

	public Permissao consultaPorCodigo(String codigo) {
		Optional<Permissao> optional = getRepository().findByCodigo(codigo);

		if (!optional.isPresent()) {
			throw new EntityNotFoundException(codigo + " not exist.");
		}
		
		return optional.get();
	}

}
