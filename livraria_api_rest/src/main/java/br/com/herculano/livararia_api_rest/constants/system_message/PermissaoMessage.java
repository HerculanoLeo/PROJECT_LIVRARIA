package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

@Component(value = "PermissaoMessage")
public class PermissaoMessage extends MessageTemplate{

	private final String NOT_FOUND = "permissao.notfound";
	
	public String getNotFound() {
		return this.NOT_FOUND;
	}

}
