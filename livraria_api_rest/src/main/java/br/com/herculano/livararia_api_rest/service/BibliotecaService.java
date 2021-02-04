package br.com.herculano.livararia_api_rest.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.BibliotecaMessage;
import br.com.herculano.livararia_api_rest.controller.request.AdministradorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.AdministradorUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaComAdministradorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.OperadorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.OperadorConsultaBibliotecaRequest;
import br.com.herculano.livararia_api_rest.controller.request.OperadorConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.OperadorUpdateRequest;
import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.BibliotecaRepository;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Service
public class BibliotecaService extends ServiceTemplate<Biblioteca, BibliotecaRepository, BibliotecaMessage> {

	@Autowired
	private UsuarioOperadorService operadorService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioAdministradorService administradorService;

	@Autowired
	public BibliotecaService(BibliotecaRepository repository, BibliotecaMessage message) {
		super(repository, message);
	}

	public Page<Biblioteca> consultaPorFiltro(BibliotecaConsultaRequest entityRequest, Pageable page) {
		return getRepository().consultaPorFiltro(entityRequest, page);
	}

	public Biblioteca cadastra(Integer idAdministrador, @Valid BibliotecaCadastroRequest entityRequest) {
		UsuarioAdministrador usuario = administradorService.consultaPorId(idAdministrador);

		Biblioteca entity = new Biblioteca(entityRequest, usuario);

		super.save(entity);

		return entity;
	}

	public Biblioteca cadastraComAdministrador(BibliotecaComAdministradorCadastroRequest entityRequest) {
		UsuarioAdministrador administrador = cadastroAdministrador(new AdministradorCadastroRequest(entityRequest));

		Biblioteca entity = new Biblioteca(entityRequest, administrador);

		entity = save(entity);

		return entity;
	}

	public Biblioteca atualiza(Integer idBiblioteca, BibliotecaUpdateRequest entityRequest) {
		Biblioteca entity = consultaPorId(idBiblioteca);

		entity.setNome(entityRequest.getNome());

		entity = save(entity);

		return entity;
	}

	public UsuarioAdministrador cadastroAdministrador(@Valid AdministradorCadastroRequest entityRequest) {
		UsuarioAdministrador entity = usuarioService.cadastroAdministrador(entityRequest);

		return entity;
	}

	public UsuarioAdministrador atualizaAdministrador(Integer idAdministrador, @Valid AdministradorUpdateRequest entityRequest) {
		UsuarioAdministrador entity = usuarioService.atualizarAdministrador(idAdministrador, entityRequest);

		return entity;
	}

	public Page<UsuarioOperador> consultaOperadores(OperadorConsultaRequest entityRequest, Pageable page) {
		Page<UsuarioOperador> entities = operadorService.consulta(entityRequest, page);

		return entities;
	}
	

	public Page<UsuarioOperador> consultaOperadores(Integer idBiblioteca, OperadorConsultaBibliotecaRequest entityRequest,	Pageable page) {
		OperadorConsultaRequest filter = OperadorConsultaRequest.builder()
				.idBiblioteca(idBiblioteca)
				.nomeOperador(entityRequest.getNomeOperador())
				.documento(entityRequest.getDocumento())
				.email(entityRequest.getEmail()).build();

		Page<UsuarioOperador> entities = operadorService.consulta(filter, page);
		
		return entities;
	}

	public UsuarioOperador cadastraOperador(Integer idBiblioteca, OperadorCadastroRequest entityRequest) {
		Biblioteca biblioteca = consultaPorId(idBiblioteca);

		entityRequest.setBiblioteca(biblioteca);

		UsuarioOperador entity = usuarioService.cadastraOperador(entityRequest);

		return entity;
	}

	public UsuarioOperador atualizaOperador(Integer idOperador, OperadorUpdateRequest entityRequest) {
		UsuarioOperador entity = usuarioService.atualizarOperador(idOperador, entityRequest);

		return entity;
	}

	public UsuarioAdministrador consultaAdministradorPorId(Integer idAdministrador) {
		UsuarioAdministrador entity = administradorService.consultaPorId(idAdministrador);

		return entity;
	}

	public UsuarioOperador consultaOperadorPorId(Integer idOperador) {
		UsuarioOperador entity = operadorService.consultaPorId(idOperador);
		
		return entity;
	}

	public List<Biblioteca> consultaPorIdUsuario(Integer id) {
		return getRepository().consultaPorIdUsuario(id);
	}

}
