package br.com.herculano.livararia_api_rest.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.entity.Configuracao;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.ConfiguracaoRepository;
import br.com.herculano.utilities.templates.CommonMessageTemplate;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Service
public class ConfiguracaoService extends ServiceTemplate<Configuracao, ConfiguracaoRepository, CommonMessageTemplate> {

	@Autowired
	public ConfiguracaoService(ConfiguracaoRepository repository, CommonMessageTemplate message) {
		super(repository, message);
	}

	public Configuracao consultaPorId(String codigo) {
		Optional<Configuracao> optional = getRepository().findById(codigo);

		if (!optional.isPresent()) {
			Object[] args = {codigo};
			
			throw new EntityNotFoundException(CommonMessageTemplate.getCodigo(message.getNotFound(), args));
		}

		return optional.get();
	}
	
}
