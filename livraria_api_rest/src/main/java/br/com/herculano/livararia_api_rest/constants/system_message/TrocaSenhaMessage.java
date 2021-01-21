package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilits.templates.MessageTemplate;

@Component(value = "TrocaSenhaMessage")
public class TrocaSenhaMessage extends MessageTemplate {

	private final String NOT_FOUND = "troca_senha.notfound";
	private final String PASSWORD_NOT_MATCH = "usuario.password_not_match";
	private final String PASSWORD_EMPTY = "troca_senha.troca_password_empty";
	private final String CODE_NOT_VALID = "troca_senha.code_not_valid";
	private final String CODE_EXPIRED = "troca_senha.code_expired";
	private final String WRONG_PASSWORD = "troca_senha.wrong_password";

	public String getNotFound() {
		return this.NOT_FOUND;
	}

	public String getPasswordNotMatch() {
		return PASSWORD_NOT_MATCH;
	}

	public String getPasswordEmpty() {
		return PASSWORD_EMPTY;
	}

	public String getCodeNotValid() {
		return CODE_NOT_VALID;
	}

	public String getCodeExpired() {
		return CODE_EXPIRED;
	}

	public String getWrongPassword() {
		return WRONG_PASSWORD;
	}

}
