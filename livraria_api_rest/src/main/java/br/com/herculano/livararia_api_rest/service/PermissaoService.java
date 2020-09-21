package br.com.herculano.livararia_api_rest.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.MessageTemplate;
import br.com.herculano.livararia_api_rest.constants.system_message.PermissaoMessage;
import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.repository.jpaRepository.PermissaoRepository;

@Service
public class PermissaoService extends ServiceTemplate<Permissao, PermissaoRepository, PermissaoMessage>{

	@Autowired
	public PermissaoService(PermissaoRepository repository, @Qualifier("PermissaoMessage") PermissaoMessage message) {
		super(repository, message);
	}

	public List<Permissao> consultaPorIdUsuario(Integer idCliente) {
		return getRepository().consultaPorIdUsuario(idCliente);
	}

	public Permissao consultaPorCodigo(String codigo) {
		Optional<Permissao> optional = getRepository().findByCodigo(codigo);

		if (!optional.isPresent()) {
			Object[] args = { codigo };
			
			throw new EntityNotFoundException(MessageTemplate.getCodigo(getMessage().getNotFound(), args));
		}
		
		return optional.get();
	}

}
