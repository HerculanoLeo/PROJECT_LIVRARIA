package br.com.herculano.livararia_api_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.UsuarioAdministradorMessage;
import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.UsuarioAdministradorRepository;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Service
public class UsuarioAdministradorService extends ServiceTemplate<UsuarioAdministrador, UsuarioAdministradorRepository, UsuarioAdministradorMessage> {

	@Autowired
	public UsuarioAdministradorService(UsuarioAdministradorRepository repository, UsuarioAdministradorMessage message) {
		super(repository, message);
	}

}
