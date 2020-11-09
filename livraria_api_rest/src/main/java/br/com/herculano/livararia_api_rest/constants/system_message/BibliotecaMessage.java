package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

@Component(value = "BibliotecaMessage")
public class BibliotecaMessage extends MessageTemplate{

	private final String NOT_FOUND = "autor.notfound";
	
	@Override
	public String getNotFound() {
		return NOT_FOUND;
	}
}
