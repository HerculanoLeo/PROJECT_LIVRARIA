package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilities.templates.MessageTemplate;

@Component(value = "AutorMessage")
public class AutorMessage extends MessageTemplate{

	private final String NOT_FOUND = "author.notfound";
	
	private final String AUTHOR_NOT_BELONG_LIBRARY = "author.not_belong_library";
	
	@Override
	public String getNotFound() {
		return NOT_FOUND;
	}
	
	public String getAuthorNotBelongLibrary() {
		return AUTHOR_NOT_BELONG_LIBRARY;
	}

}
