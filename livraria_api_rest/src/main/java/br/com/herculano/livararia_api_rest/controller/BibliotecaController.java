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

import br.com.herculano.livararia_api_rest.controller.request.biblioteca.AdministradorUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.BibliotecaCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.BibliotecaComAdministradorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.BibliotecaConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.BibliotecaUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.OperadorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.OperadorConsultaBibliotecaRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.OperadorConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.biblioteca.OperadorUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.response.BibliotecaComAdministradorResponse;
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
	public ResponseEntity<BibliotecaComAdministradorResponse> cadastrarBibliotecaComAdministrador(
			@RequestBody @Valid BibliotecaComAdministradorCadastroRequest entityRequest, HttpServletResponse response) {
		Biblioteca entity = service.cadastraComAdministrador(entityRequest);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new BibliotecaComAdministradorResponse(entity));
	}

	@PutMapping("{idBiblioteca}")
	public ResponseEntity<BibliotecaComAdministradorResponse> atualizaBiblioteca(@RequestBody @Valid BibliotecaUpdateRequest entityRequest,
			@PathVariable(name = "idBiblioteca") Integer idBiblioteca) {
		Biblioteca entity = service.atualiza(idBiblioteca, entityRequest);

		return ResponseEntity.ok(new BibliotecaComAdministradorResponse(entity));
	}

	@GetMapping("/administrador/{idAdministrador}") //TODO teste parou aq.
	public ResponseEntity<UsuarioAdministradorResponse> consultaAdministradorPorId(@PathVariable Integer idAdministrador) {
		UsuarioAdministrador entity = service.consultaAdministradorPorId(idAdministrador);

		return ResponseEntity.ok(new UsuarioAdministradorResponse(entity));
	}

//	@PostMapping("/administrador") TODO não faz sentido existir cadastro de administrador sem biblioteca.
//	public ResponseEntity<UsuarioAdministradorResponse> cadastrarAdministrador(@RequestBody @Valid AdministradorCadastroRequest entityRequest) {
//		UsuarioAdministrador entity = service.cadastroAdministrador(entityRequest);
//
//		return ResponseEntity.ok(new UsuarioAdministradorResponse(entity));
//	}

	@PutMapping("/administrador/{idAdministrador}")
	public ResponseEntity<UsuarioAdministradorResponse> atualizaAdministrador(@RequestBody @Valid AdministradorUpdateRequest entityRequest,
			@PathVariable("idAdministrador") Integer idAdministrador) {
		UsuarioAdministrador entity = service.atualizaAdministrador(idAdministrador, entityRequest);

		return ResponseEntity.ok(new UsuarioAdministradorResponse(entity));
	}

	@PostMapping("/administrador/{idAdministrador}/biblioteca")
	public ResponseEntity<BibliotecaComAdministradorResponse> adicionarBiblioteca(@RequestBody @Valid BibliotecaCadastroRequest entityRequest,
			@PathVariable("idAdministrador") Integer idAdministrador, HttpServletResponse response) {
		Biblioteca entity = service.cadastra(idAdministrador, entityRequest);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new BibliotecaComAdministradorResponse(entity));
	}

	@GetMapping("/operador")
	public ResponseEntity<Page<UsuarioOperadorResponse>> consultaOperador(OperadorConsultaRequest entityRequest, Pageable page) {
		Page<UsuarioOperador> entities = service.consultaOperadores(entityRequest, page);

		return ResponseEntity.ok(entities.map(UsuarioOperadorResponse::new));
	}

	@GetMapping("/administrador/{idAdministrador}/operador")
	public ResponseEntity<Page<UsuarioOperadorResponse>> consultaOperadorAdministrador(@PathVariable("idAdministrador") Integer idAdministrador,
			OperadorConsultaBibliotecaRequest entityRequest, Pageable page) {
		Page<UsuarioOperador> entities = service.consultaOperadorAdministrador(idAdministrador, entityRequest, page);
		
		return ResponseEntity.ok(entities.map(UsuarioOperadorResponse::new));
	}
	
	@GetMapping("/{idBiblioteca}/operador")
	public ResponseEntity<Page<UsuarioOperadorResponse>> consultaOperadorBiblioteca(@PathVariable("idBiblioteca") Integer idBiblioteca,
			OperadorConsultaBibliotecaRequest entityRequest, Pageable page) {
		Page<UsuarioOperador> entities = service.consultaOperadores(idBiblioteca, entityRequest, page);

		return ResponseEntity.ok(entities.map(UsuarioOperadorResponse::new));
	}

	@GetMapping("/operador/{idOperador}")
	public ResponseEntity<UsuarioOperadorResponse> consultaOperadorPorId(@PathVariable("idOperador") Integer idOperador) {
		UsuarioOperador entity = service.consultaOperadorPorId(idOperador);

		return ResponseEntity.ok(new UsuarioOperadorResponse(entity));
	}
	
	@PostMapping("/{idBiblioteca}/operador")
	public ResponseEntity<?> cadastrarOperador(@PathVariable("idBiblioteca") Integer idBiblioteca, @RequestBody @Valid OperadorCadastroRequest entityRequest,
			HttpServletResponse response) {
		UsuarioOperador entity = service.cadastraOperador(idBiblioteca, entityRequest);
		
		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioOperadorResponse(entity));
	}

	@PutMapping("/operador/{idOperador}")
	public ResponseEntity<UsuarioOperadorResponse> atualizaOperador(@PathVariable("idOperador") Integer idOperador,
			@RequestBody @Valid OperadorUpdateRequest entityRequest) {
		UsuarioOperador entity = service.atualizaOperador(idOperador, entityRequest);

		return ResponseEntity.ok(new UsuarioOperadorResponse(entity));
	}

}
