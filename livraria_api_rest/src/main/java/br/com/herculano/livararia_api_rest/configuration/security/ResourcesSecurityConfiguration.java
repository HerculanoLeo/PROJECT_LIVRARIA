package br.com.herculano.livararia_api_rest.configuration.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;

@Component("resourcesSecurity")
public class ResourcesSecurityConfiguration {
	
	public boolean isAcessoDadosUsuario(Authentication authentication, Integer idUsuario) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		if(usuario.isROOT() && usuario.getAuthorities().contains(Permissao.builder().codigo("CONSULTA_USUARIO_ID").build())) {
			return true;
		}
		
		if(usuario.getId().equals(idUsuario)) {
			return true;
		}
		
		return false;
		
	}
	
	public boolean isAtualizaDadosUsuarioCliente(Authentication authentication, Integer idUsuario) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		if(usuario.isROOT() && usuario.getAuthorities().contains(Permissao.builder().codigo("ATUALIZA_USUARIO_CLIENTE").build())) {
			return true;
		}
		
		if(usuario.getId().equals(idUsuario)) {
			return true;
		}
		
		return false;
		
	}
	
	public boolean isAtualizaDadosUsuarioRoot(Authentication authentication, Integer idUsuario) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		if(usuario.isROOT() && usuario.getAuthorities().contains(Permissao.builder().codigo("ATUALIZA_USUARIO_ROOT").build())) {
			return true;
		}
		
		if(usuario.getId().equals(idUsuario)) {
			return true;
		}
		
		return false;
		
	}

	public boolean isIdBiblioteca(Authentication authentication, Integer idBiblioteca) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		if(usuario.isROOT()) {
			return true;
		}
		
		if(usuario.isBiblioteca()) {
			return usuario.getIdsBiblioteca().contains(idBiblioteca);
		}
		
		return false;
	}
	
	public boolean isAcessoDadosAdministrador(Authentication authentication, Integer idAdministrador) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		if(usuario.isROOT()) {
			return true;
		}
		
		if(usuario instanceof UsuarioOperador) {
			return ((UsuarioOperador) usuario).getBiblioteca().getAdministrador().getId().equals(idAdministrador);
		}
		
		if(usuario instanceof UsuarioAdministrador) {
			return usuario.getId().equals(idAdministrador);
		}
		
		return false;
	}
	
}
