package br.com.herculano.livararia_api_rest.exception.custom;

public class ConfirmPasswordException extends RuntimeException {

	private static final long serialVersionUID = 288762879652523340L;

	public ConfirmPasswordException(String string) {
		super(string);
	}
}
