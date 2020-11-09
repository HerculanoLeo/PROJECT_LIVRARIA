package br.com.herculano.livararia_api_rest.auth.token;


import static br.com.herculano.livararia_api_rest.auth.token.Constants.TOKEN_PREFIX;

import java.util.Date;

import lombok.Data;

@Data
public class AuthToken {

	private String tipo = TOKEN_PREFIX;
	private String token;
	private Date expireToken;
	private Object usuario;

	public AuthToken(String token, Date expireToken, Object usuario) {
		this.token = token;
		this.expireToken = expireToken;
		this.usuario = usuario;
	}

}
