package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilities.templates.MessageTemplate;

@Component(value = "BibliotecaMessage")
public class BibliotecaMessage extends MessageTemplate{

	private final String NOT_FOUND = "autor.notfound";

	private final String NOT_ALLOWED_USER_TYPE = "biblioteca.not_allowed_user_type";
	
	@Override
	public String getNotFound() {
		return NOT_FOUND;
	}
	
	public String getNotAllowedUserType() {
		return this.NOT_ALLOWED_USER_TYPE;
	}
}
