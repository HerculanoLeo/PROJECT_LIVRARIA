package br.com.herculano.livararia_api_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.UsuarioOperadorMessage;
import br.com.herculano.livararia_api_rest.controller.request.OperadorConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.UsuarioOperadorRepository;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Service
public class UsuarioOperadorService extends ServiceTemplate<UsuarioOperador, UsuarioOperadorRepository, UsuarioOperadorMessage>{

	@Autowired
	public UsuarioOperadorService(UsuarioOperadorRepository repository, UsuarioOperadorMessage message) {
		super(repository, message);
	}

	public Page<UsuarioOperador> consulta(OperadorConsultaRequest entityRequest, Pageable page) {
		return getRepository().consulta(entityRequest, page);
	}


}
