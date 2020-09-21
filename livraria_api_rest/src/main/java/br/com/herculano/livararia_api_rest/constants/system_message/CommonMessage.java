package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

@Component(value = "CommonMessage")
public class CommonMessage extends MessageTemplate {

	private final String NOT_FOUND = "comum.notfound";
	private final String JSON_MALFORMED = "comum.jsonMalformed";
	private final String VALIDATION_ERROR = "comum.validationError";
	
	private final String USER_NOT_FOUND = "comum.user_not_found";
	private final String USER_OR_PASSWORD_NOTFOUND="comum.user_or_password_incorrect";
	
	public String getNotFound() {
		return this.NOT_FOUND;
	}

	public String getJsonMalformed() {
		return JSON_MALFORMED;
	}

	public String getValidationError() {
		return VALIDATION_ERROR;
	}

	public String getUserNotFound() {
		return USER_NOT_FOUND;
	}
	
	public String getUserOrPasswordIncorrect() {
		return USER_OR_PASSWORD_NOTFOUND;
	}
}
