package br.com.herculano.livararia_api_rest.service;

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

	public Biblioteca cadastra(@Valid BibliotecaCadastroRequest entityRequest) {
		UsuarioAdministrador usuario = administradorService.consultaPorId(entityRequest.getIdAdministrador());

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

	public Biblioteca atualiza(BibliotecaUpdateRequest entityRequest) {
		Biblioteca entity = consultaPorId(entityRequest.getId());

		entity.setNome(entityRequest.getNome());

		entity = save(entity);

		return entity;
	}

	public UsuarioAdministrador cadastroAdministrador(@Valid AdministradorCadastroRequest entityRequest) {
		UsuarioAdministrador entity = usuarioService.cadastroAdministrador(entityRequest);

		return entity;
	}

	public UsuarioAdministrador atualizaAdministrador(@Valid AdministradorUpdateRequest entityRequest) {
		UsuarioAdministrador entity = usuarioService.atualizarAdministrador(entityRequest);

		return entity;
	}

	public Page<UsuarioOperador> consultaOperadores(OperadorConsultaRequest entityRequest, Pageable page) {
		Page<UsuarioOperador> entities = operadorService.consulta(entityRequest, page);

		return entities;
	}

	public UsuarioOperador cadastraOperador(OperadorCadastroRequest entityRequest) {
		Biblioteca biblioteca = consultaPorId(entityRequest.getIdBiblioteca());

		entityRequest.setBiblioteca(biblioteca);

		UsuarioOperador entity = usuarioService.cadastraOperador(entityRequest);

		return entity;
	}

	public UsuarioOperador atualizaOperador(@Valid OperadorUpdateRequest entityRequest) {
		UsuarioOperador entity = usuarioService.atualizarOperador(entityRequest);

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

}
