package br.com.herculano.livararia_api_rest.repository.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.utilities.exceptions.DadosInvalidosException;

@Component
public class ContextUtils {

	public static Usuario getUsuarioAutenticado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static boolean isBiblioteca() {
		return getUsuarioAutenticado().isBiblioteca();
	}

	public static boolean isIdBibliotecaPresent(Integer id) {
		return getUsuarioAutenticado().getIdsBiblioteca().contains(id);
	}

	//TODO continuar a validade
	public static boolean validaBiblioteca(Integer idBiblioteca, Integer idUsuarioAdministrador) {
		if (isBiblioteca()) {
			if (null != idBiblioteca && !isIdBibliotecaPresent(idBiblioteca)) {
				throw new DadosInvalidosException(null);
			}

			if (null != idUsuarioAdministrador && !getUsuarioAutenticado().getIdUsuarioAdministrador().equals(idUsuarioAdministrador)) {
				throw new DadosInvalidosException(null);
			}
		}
		return true;
	}

}
