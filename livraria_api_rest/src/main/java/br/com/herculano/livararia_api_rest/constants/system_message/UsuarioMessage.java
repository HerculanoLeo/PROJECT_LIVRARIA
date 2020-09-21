package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

@Component(value = "UsuarioMessage")
public class UsuarioMessage extends MessageTemplate{

	private final String NOT_FOUND = "usuario.notfound";
	private final String EMAIL_NOT_FOUND = "usuario.email_notfound";
	private final String PASSWORD_NOT_MATCH = "usuario.password_not_match";
	private final String EMPTY_GROUP_USER = "usuario.empty_group_not_valid";
	
	@Override
	public String getNotFound() {
		return NOT_FOUND;
	}
	
	public String getEmailNotFound() {
		return EMAIL_NOT_FOUND;
	}
	
	public String getEmptyGroupUser() {
		return EMPTY_GROUP_USER;
	}
	
	public String getPasswordNotMatch() {
		return PASSWORD_NOT_MATCH;
	}
}
