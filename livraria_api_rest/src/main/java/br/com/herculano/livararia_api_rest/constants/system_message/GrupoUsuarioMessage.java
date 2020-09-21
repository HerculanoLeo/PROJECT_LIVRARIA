package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

@Component(value = "GrupoUsuarioMessage")
public class GrupoUsuarioMessage extends MessageTemplate{

	private final String NOT_FOUND = "grupo.notfound";
	
	@Override
	public String getNotFound() {
		return NOT_FOUND;
	}

}
