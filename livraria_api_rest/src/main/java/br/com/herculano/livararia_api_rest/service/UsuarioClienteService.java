package br.com.herculano.livararia_api_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.UsuarioClienteMessage;
import br.com.herculano.livararia_api_rest.entity.UsuarioCliente;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.UsuarioClienteRepository;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Service
public class UsuarioClienteService extends ServiceTemplate<UsuarioCliente, UsuarioClienteRepository, UsuarioClienteMessage> {

	@Autowired
	public UsuarioClienteService(UsuarioClienteRepository repository, UsuarioClienteMessage message) {
		super(repository, message);
	}

}
