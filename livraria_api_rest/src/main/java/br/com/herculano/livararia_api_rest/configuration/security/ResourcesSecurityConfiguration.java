package br.com.herculano.livararia_api_rest.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.herculano.livararia_api_rest.controller.request.autor.AutorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.livro.LivroCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.perfil.PerfilConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioAtualizaPerfilRequest;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.entity.Livro;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;
import br.com.herculano.livararia_api_rest.service.AutorService;
import br.com.herculano.livararia_api_rest.service.LivroService;
import br.com.herculano.livararia_api_rest.service.PerfilService;
import br.com.herculano.livararia_api_rest.service.UsuarioOperadorService;
import br.com.herculano.livararia_api_rest.service.UsuarioService;

@Component("resourcesSecurity")
public class ResourcesSecurityConfiguration {

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioOperadorService operadorService;

	@Autowired
	private AutorService autorService;

	@Autowired
	private LivroService livroService;

	// Acessos Usuarios
	public boolean isAcessoDadosUsuario(Authentication authentication, Integer idUsuario) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();
			
			if (usuario.getId().equals(idUsuario)) {
				return true;
			}

			if (usuario.isRoot() && usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CONSULTA_USUARIO_ID").build())) {
				return true;
			}
		}

		return false;
	}

	public boolean isAtualizaDadosUsuarioCliente(Authentication authentication, Integer idUsuario) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();
			
			if (usuario.getId().equals(idUsuario)) {
				return true;
			}

			if (usuario.isRoot() && usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_ATUALIZA_USUARIO_CLIENTE").build())) {
				return true;
			}
		}

		return false;
	}

	public boolean isAtualizaDadosUsuarioRoot(Authentication authentication, Integer idUsuario) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();
			
			if (usuario.getId().equals(idUsuario)) {
				return true;
			}

			if (usuario.isRoot() && usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CADASTRO_USUARIO_ROOT").build())) {
				return true;
			}
		}

		return false;
	}

	public boolean isAtualizaPerfilUsuario(Authentication authentication, UsuarioAtualizaPerfilRequest entityRequest) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_ATUALIZA_PERFIL_USUARIO").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				Usuario entity = usuarioService.consultaPorId(entityRequest.getIdUsuario());

				if (usuario.isBiblioteca()) {
					
					if (entity instanceof UsuarioOperador) {
						return ((UsuarioOperador) entity).getBiblioteca().getAdministrador().getId().equals(usuario.getIdUsuarioAdministrador());
					}
				}
			}
		}

		return false;
	}

	// Acesso Perfil
	public boolean isAcessoPerfis(Authentication authentication, PerfilConsultaRequest entityRequest) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.isRoot()) {
				return true;
			}

			if (usuario.isBiblioteca()) {
				if (null == entityRequest.getIdAdministrador()) {
					entityRequest.setIdAdministrador(usuario.getIdUsuarioAdministrador());

					return true;
				}

				if (entityRequest.getIdAdministrador().equals(usuario.getIdUsuarioAdministrador())) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isAcessoIdPerfil(Authentication authentication, Integer idPerfil) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getPerfil().getId().equals(idPerfil)) {
				return true;
			}

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CONSULTA_PERFIL_ID").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				Perfil perfil = perfilService.consultaPorId(idPerfil);

				if (usuario.getIdUsuarioAdministrador().equals(perfil.getAdministrador().getId())) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isCadastroPerfil(Authentication authentication, Integer idAdministrador) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CADASTRO_PERFIL").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isOperador()) {
					return ((UsuarioOperador) usuario).getBiblioteca().getAdministrador().getId().equals(idAdministrador);
				}

				if (usuario.isAdministrador()) {
					return usuario.getId().equals(idAdministrador);
				}
			}
		}

		return false;
	}

	// Acesso Biblioteca
	public boolean isAtualizaBiblioteca(Authentication authentication, Integer idBiblioteca) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CADASTRO_BIBLIOTECA").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isBiblioteca() && usuario.getIdsBiblioteca().contains(idBiblioteca)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isConsultaAdministrador(Authentication authentication, Integer idAdministrador) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.isAdministrador()) {
				return usuario.getId().equals(idAdministrador);
			}

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CONSULTA_ADMINSITRADOR_POR_ID").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.getIdUsuarioAdministrador().equals(idAdministrador)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isAtualizaAdministrador(Authentication authentication, Integer idAdministrador) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_ATUALIZA_ADMINSITRADOR").build())) {
				if (usuario.isAdministrador()) {
					return usuario.getId().equals(idAdministrador);
				}

				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.getIdUsuarioAdministrador().equals(idAdministrador)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isCadastraNovaBibliotecaNoAdministrador(Authentication authentication, Integer idAdministrador) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.isAdministrador()) {
				return usuario.getId().equals(idAdministrador);
			}

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CADASTRO_BIBLIOTECA").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.getIdUsuarioAdministrador().equals(idAdministrador)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isConsultaTodosOperadores(Authentication authentication) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.isRoot() && usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CONSULTA_OPERADORES").build())) {
				return true;
			}
		}

		return false;
	}

	public boolean isConsultaOperadoresIdAdministrador(Authentication authentication, Integer idAdministrador) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CONSULTA_OPERADORES").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isBiblioteca()) {
					if (usuario.getId().equals(idAdministrador)) {
						return true;
					}

					if (usuario.getIdUsuarioAdministrador().equals(idAdministrador)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isConsultaOperadoresIdBiblioteca(Authentication authentication, Integer idBiblioteca) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CONSULTA_OPERADORES").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isBiblioteca()) {
					if (usuario.isAdministrador() && usuario.getIdsBiblioteca().contains(idBiblioteca)) {
						return true;
					}

					if (usuario.getIdsBiblioteca().contains(idBiblioteca)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isConsultaIdOperador(Authentication authentication, Integer idOperador) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CONSULTA_OPERADORES").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isBiblioteca()) {
					UsuarioOperador operador = operadorService.consultaPorId(idOperador);

					if (usuario.isAdministrador() && operador.getIdUsuarioAdministrador().equals(usuario.getId())) {
						return true;
					}

					if (usuario.getIdsBiblioteca().contains(operador.getBiblioteca().getId())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isCadastroOperador(Authentication authentication, Integer idBiblioteca) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CADASTRO_OPERADOR").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isBiblioteca()) {
					if (usuario.isAdministrador() && usuario.getIdsBiblioteca().contains(idBiblioteca)) {
						return true;
					}

					if (usuario.getIdsBiblioteca().contains(idBiblioteca)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isAtualizaOperador(Authentication authentication, Integer idOperador) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getId().equals(idOperador)) {
				return true;
			}

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CADASTRO_OPERADOR").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isBiblioteca()) {
					UsuarioOperador usuarioOperador = operadorService.consultaPorId(idOperador);

					if (usuario.isAdministrador() && usuario.getIdsBiblioteca().contains(usuarioOperador.getBiblioteca().getId())) {
						return true;
					}

					if (usuario.getIdsBiblioteca().contains(usuarioOperador.getBiblioteca().getId())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	// Acesso Autor
	public boolean isCadastroAutor(Authentication authentication, AutorCadastroRequest entityRequest) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CADASTRO_AUTOR").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isBiblioteca()) {
					if (usuario.isAdministrador() && usuario.getIdsBiblioteca().contains(entityRequest.getIdBiblioteca())) {
						return true;
					}

					if (usuario.getIdsBiblioteca().contains(entityRequest.getIdBiblioteca())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isAtualizarAutor(Authentication authentication, Integer idAutor) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CADASTRO_AUTOR").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isBiblioteca()) {
					Autor autor = autorService.consultaPorId(idAutor);

					if (usuario.isAdministrador() && usuario.getIdsBiblioteca().contains(autor.getBiblioteca().getId())) {
						return true;
					}

					if (usuario.getIdsBiblioteca().contains(autor.getBiblioteca().getId())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	// Acesso Livro
	public boolean isCadastroLivro(Authentication authentication, LivroCadastroRequest entityRequest) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CADASTRO_LIVRO").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isBiblioteca()) {
					if (usuario.isAdministrador() && usuario.getIdsBiblioteca().contains(entityRequest.getIdBiblioteca())) {
						return true;
					}

					if (usuario.getIdsBiblioteca().contains(entityRequest.getIdBiblioteca())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isAtualizarLivro(Authentication authentication, Integer idLivro) {
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			Usuario usuario = (Usuario) authentication.getPrincipal();

			if (usuario.getAuthorities().contains(Permissao.builder().codigo("ROLE_CADASTRO_LIVRO").build())) {
				if (usuario.isRoot()) {
					return true;
				}

				if (usuario.isBiblioteca()) {
					Livro livro = livroService.consultaPorId(idLivro);

					if (usuario.isAdministrador() && usuario.getIdsBiblioteca().contains(livro.getBiblioteca().getId())) {
						return true;
					}

					if (usuario.getIdsBiblioteca().contains(livro.getBiblioteca().getId())) {
						return true;
					}
				}
			}
		}

		return false;
	}
}
