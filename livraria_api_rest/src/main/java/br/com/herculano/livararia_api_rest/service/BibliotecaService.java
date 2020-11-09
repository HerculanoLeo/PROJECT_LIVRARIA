package br.com.herculano.livararia_api_rest.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.BibliotecaMessage;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaOperadorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioCadastroRequest;
import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.BibliotecaRepository;

@Service
public class BibliotecaService extends ServiceTemplate<Biblioteca, BibliotecaRepository, BibliotecaMessage> {
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	public BibliotecaService(BibliotecaRepository repository, @Qualifier("BibliotecaMessage") BibliotecaMessage message) {
		super(repository, message);
	}
	
	public Page<Biblioteca> consultaPorFiltro(BibliotecaConsultaRequest entityRequest, Pageable page) {
		return getRepository().consultaPorFiltro(entityRequest, page);
	}

	public Biblioteca consultaPorUsuarioId(Integer idUsuario) {
		return getRepository().consultaPorUsuarioId(idUsuario);
	}

	public Biblioteca cadastra(BibliotecaCadastroRequest entityRequest) {
		UsuarioCadastroRequest usuarioRequest = new UsuarioCadastroRequest(entityRequest.getNomeAdministrador(),
				entityRequest.getEmail(), entityRequest.getSenha(), entityRequest.getConfirmeSenha());
		
		Usuario usuario = usuarioService.cadastra(usuarioRequest);
		
		Biblioteca entity = new Biblioteca(entityRequest, usuario);
		
		entity = save(entity);
		
		return entity;
	}

	public Biblioteca atualiza(BibliotecaUpdateRequest entityRequest, Integer idBiblioteca) {
		Biblioteca entity = consultaPorId(idBiblioteca);
		
		entity.setNome(entityRequest.getNome());
		
		entity = save(entity);
		
		return entity;
	}

	public Page<Usuario> consultaOperadores(Integer idBiblioteca, Pageable page) {
		Biblioteca entity = consultaPorId(idBiblioteca);
		
		return usuarioService.consultaOperadoresPorIdBiblioteca(entity.getId(), page);
	}

	public Usuario cadastraOperador(Integer idBiblioteca, BibliotecaOperadorCadastroRequest entityRequest) {
		Biblioteca biblioteca = consultaPorId(idBiblioteca);

		String password = RandomStringUtils.randomAlphanumeric(6);
		System.out.println("Password: " + password);

		Usuario entity = new Usuario(entityRequest.getEmail(), entityRequest.getNomeOperador(), password);
		
		entity = usuarioService.save(entity);
		
		biblioteca.getOperadores().add(entity);
		save(biblioteca);
		
		return entity;
	}

}
