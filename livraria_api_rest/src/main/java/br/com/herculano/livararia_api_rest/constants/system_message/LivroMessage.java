package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilities.templates.MessageTemplate;

@Component(value = "LivroMessage")
public class LivroMessage extends MessageTemplate{

	private final String NOT_FOUND = "book.notfound";
	
	private final String BOOK_NOT_BELONG_LIBRARY = "book.not_belong_library";
	
	public String getNotFound() {
		return this.NOT_FOUND;
	}

	public String getBookNotBelongLibrary() {
		return this.BOOK_NOT_BELONG_LIBRARY;
	}
	
}
