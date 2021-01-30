package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilities.templates.MessageTemplate;

@Component(value = "UsuarioMessage")
public class UsuarioMessage extends MessageTemplate {

	private final String NOT_FOUND = "user.notfound";
	private final String AUTHENTICATION_NOT_FOUND = "user.authentication.notfound";
	private final String EMAIL_NOT_FOUND = "user.email_notfound";
	private final String PASSWORD_NOT_MATCH = "user.password_not_match";
	private final String EMPTY_PROFILE_USER = "user.empty_profile_not_valid";
	private final String PROFILE_NOT_COMPATIBLE = "user.profile_not_compatible";
	private final String EMAIL_ALREADY_EXIST = "user.email_already_exist";

	@Override
	public String getNotFound() {
		return NOT_FOUND;
	}
	
	public String getAuthenticationNotFound() {
		return AUTHENTICATION_NOT_FOUND;
	}

	public String getEmailNotFound() {
		return EMAIL_NOT_FOUND;
	}

	public String getEmptyProfileUser() {
		return EMPTY_PROFILE_USER;
	}

	public String getPasswordNotMatch() {
		return PASSWORD_NOT_MATCH;
	}
	
	public String getProfileNotCompatible() {
		return PROFILE_NOT_COMPATIBLE;
	}
	
	public String getEmailAlreadyExist() {
		return EMAIL_ALREADY_EXIST;
	}
}
