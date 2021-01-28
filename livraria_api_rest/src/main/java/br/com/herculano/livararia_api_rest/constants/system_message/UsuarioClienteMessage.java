package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilities.templates.MessageTemplate;

@Component(value = "UsuarioClienteMessage")
public class UsuarioClienteMessage extends MessageTemplate {

	private final String NOT_FOUND = "client.notfound";
	
	@Override
	public String getNotFound() {
		return NOT_FOUND;
	}

}
