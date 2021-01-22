package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilities.templates.MessageTemplate;

@Component(value = "AutorMessage")
public class AutorMessage extends MessageTemplate{

	private final String NOT_FOUND = "biblioteca.notfound";
	
	@Override
	public String getNotFound() {
		return NOT_FOUND;
	}

}
