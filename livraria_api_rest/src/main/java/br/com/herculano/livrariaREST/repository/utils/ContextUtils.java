package br.com.herculano.livrariaREST.repository.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class ContextUtils {
	
	public static String getUsuarioAutenticado() {
		return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
}
