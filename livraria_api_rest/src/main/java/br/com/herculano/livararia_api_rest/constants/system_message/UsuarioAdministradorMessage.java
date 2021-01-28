package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilities.templates.MessageTemplate;

@Component(value = "UsuarioAdministradorMessage")
public class UsuarioAdministradorMessage extends MessageTemplate {

	private final String NOT_FOUND = "administrator.notfound";

	@Override
	public String getNotFound() {
		return NOT_FOUND;
	}
	
}
