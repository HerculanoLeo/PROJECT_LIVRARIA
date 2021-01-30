package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilities.templates.MessageTemplate;

@Component(value = "PermissaoMessage")
public class PermissaoMessage extends MessageTemplate{

	private final String NOT_FOUND = "permission.notfound";
	
	public String getNotFound() {
		return this.NOT_FOUND;
	}

}
