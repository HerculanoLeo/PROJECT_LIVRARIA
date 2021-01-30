package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilities.templates.MessageTemplate;

@Component(value = "LivroMessage")
public class LivroMessage extends MessageTemplate{

	private final String NOT_FOUND = "book.notfound";
	
	public String getNotFound() {
		return this.NOT_FOUND;
	}
	
}
