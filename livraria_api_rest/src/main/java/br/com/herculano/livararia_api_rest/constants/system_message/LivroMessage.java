package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

@Component(value = "LivroMessage")
public class LivroMessage extends MessageTemplate{

	private final String NOT_FOUND = "livro.notfound";
	
	public String getNotFound() {
		return this.NOT_FOUND;
	}
	
}
