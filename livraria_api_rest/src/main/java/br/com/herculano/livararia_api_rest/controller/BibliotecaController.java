package br.com.herculano.livararia_api_rest.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.herculano.livararia_api_rest.controller.request.AdministradorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.AdministradorUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaComAdministradorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.OperadorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.OperadorConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.OperadorUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.response.BibliotecaResponse;
import br.com.herculano.livararia_api_rest.controller.response.UsuarioAdministradorResponse;
import br.com.herculano.livararia_api_rest.controller.response.UsuarioOperadorResponse;
import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;
import br.com.herculano.livararia_api_rest.event.CreatedEvent;
import br.com.herculano.livararia_api_rest.service.BibliotecaService;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

	@Autowired
	private BibliotecaService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<Page<BibliotecaResponse>> consultaPorFiltro(BibliotecaConsultaRequest entityRequest, Pageable page) {
		Page<Biblioteca> entities = service.consultaPorFiltro(entityRequest, page);

		Page<BibliotecaResponse> response = entities.map(BibliotecaResponse::new);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{idBiblioteca}")
	public ResponseEntity<BibliotecaResponse> consultaPorId(@PathVariable(name = "idBiblioteca") Integer idBiblioteca) {
		Biblioteca entity = service.consultaPorId(idBiblioteca);

		return ResponseEntity.ok(new BibliotecaResponse(entity));
	}

	@PostMapping
	public ResponseEntity<BibliotecaResponse> cadastrarBibliotecaComAdministrador(
			@RequestBody @Valid BibliotecaComAdministradorCadastroRequest entityRequest, HttpServletResponse response) {
		Biblioteca entity = service.cadastraComAdministrador(entityRequest);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new BibliotecaResponse(entity));
	}

	@PutMapping
	public ResponseEntity<BibliotecaResponse> atualizaBiblioteca(@RequestBody @Valid BibliotecaUpdateRequest entityRequest) {
		Biblioteca entity = service.atualiza(entityRequest);

		return ResponseEntity.ok(new BibliotecaResponse(entity));
	}

	@GetMapping("/administrador/{idAdministrador}")
	public ResponseEntity<UsuarioAdministradorResponse> consultaAdministradorPorId(@PathVariable Integer idAdministrador) {
		UsuarioAdministrador entity = service.consultaAdministradorPorId(idAdministrador);

		return ResponseEntity.ok(new UsuarioAdministradorResponse(entity));
	}

	@PostMapping("/administrador")
	public ResponseEntity<UsuarioAdministradorResponse> cadastrarAdministrador(
			@RequestBody @Valid AdministradorCadastroRequest entityRequest) {
		UsuarioAdministrador entity = service.cadastroAdministrador(entityRequest);

		return ResponseEntity.ok(new UsuarioAdministradorResponse(entity));
	}

	@PutMapping("/administrador")
	public ResponseEntity<UsuarioAdministradorResponse> atualizaAdministrador(
			@RequestBody @Valid AdministradorUpdateRequest entityRequest) {
		UsuarioAdministrador entity = service.atualizaAdministrador(entityRequest);

		return ResponseEntity.ok(new UsuarioAdministradorResponse(entity));
	}

	@PostMapping("/administrador/biblioteca")
	public ResponseEntity<BibliotecaResponse> adicionarBiblioteca(@RequestBody @Valid BibliotecaCadastroRequest entityRequest,
			HttpServletResponse response) {
		Biblioteca entity = service.cadastra(entityRequest);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new BibliotecaResponse(entity));
	}

	@GetMapping("/operador")
	public ResponseEntity<Page<UsuarioOperadorResponse>> consultaOperador(OperadorConsultaRequest entityRequest, Pageable page) {
		Page<UsuarioOperador> entities = service.consultaOperadores(entityRequest, page);

		return ResponseEntity.ok(entities.map(UsuarioOperadorResponse::new));
	}

	@GetMapping("/operador/{idOperador}")
	public ResponseEntity<UsuarioOperadorResponse> consultaOperadorPorId(@PathVariable Integer idOperador) {
		UsuarioOperador entity = service.consultaOperadorPorId(idOperador);
				
		return ResponseEntity.ok(new UsuarioOperadorResponse(entity));
	}

	@PutMapping("/operador")
	public ResponseEntity<UsuarioOperadorResponse> atualizaOperador(@RequestBody @Valid OperadorUpdateRequest entityRequest) {
		UsuarioOperador entity = service.atualizaOperador(entityRequest);

		return ResponseEntity.ok(new UsuarioOperadorResponse(entity));
	}

	@PostMapping("/{idBiblioteca}/operador")
	public ResponseEntity<?> cadastrarOperador(@RequestBody @Valid OperadorCadastroRequest entityRequest,
			HttpServletResponse response) {
		UsuarioOperador entity = service.cadastraOperador(entityRequest);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioOperadorResponse(entity));
	}
}
