package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.stereotype.Component;

import br.com.herculano.utilities.templates.MessageTemplate;

@Component(value = "PerfilMessage")
public class PerfilMessage extends MessageTemplate{

	private final String NOT_FOUND = "perfil.notfound";
	
	private final String NOT_ALLOWED_TYPE_PROFILE = "perfil.not_allowed_type_profile";

	private final String NOT_ALLOWED_TYPE_PROFILE_TYPE_PERMISSION = "perfil.not_allowed_type_profile_type_permission";
	
	@Override
	public String getNotFound() {
		return NOT_FOUND;
	}

	public String getNotAllowedTypeProfile() {
		return NOT_ALLOWED_TYPE_PROFILE;
	}
	
	public String getNotAllowedTypeProfileTypePermission() {
		return NOT_ALLOWED_TYPE_PROFILE_TYPE_PERMISSION;
	}
}
