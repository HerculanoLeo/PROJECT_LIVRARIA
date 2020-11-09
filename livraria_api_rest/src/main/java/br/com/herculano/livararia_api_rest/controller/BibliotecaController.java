package br.com.herculano.livararia_api_rest.controller;

import javax.servlet.http.HttpServletRequest;
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

import br.com.herculano.livararia_api_rest.controller.request.BibliotecaCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaOperadorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.BibliotecaUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.response.BibliotecaResponse;
import br.com.herculano.livararia_api_rest.controller.response.UsuarioResponse;
import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.entity.Usuario;
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
	public ResponseEntity<?> consultaPorFiltro(BibliotecaConsultaRequest entityRequest, Pageable page, HttpServletRequest request) {
		Page<Biblioteca> entities = service.consultaPorFiltro(entityRequest, page);
		
		Page<BibliotecaResponse> response = entities.map(BibliotecaResponse::new);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{idBiblioteca}")
	public ResponseEntity<?> consultaPorId(@PathVariable(name = "idBiblioteca") Integer idBiblioteca, HttpServletRequest request) {
		Biblioteca entity = service.consultaPorId(idBiblioteca);
		
		return ResponseEntity.ok(new BibliotecaResponse(entity));
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrarBiblioteca(@RequestBody @Valid BibliotecaCadastroRequest entityRequest, HttpServletResponse response) {
		Biblioteca entity = service.cadastra(entityRequest);
		
		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new BibliotecaResponse(entity));
	}
	
	@PutMapping("/{idBiblioteca}")
	public ResponseEntity<?> atualizaBiblioteca(@RequestBody @Valid BibliotecaUpdateRequest entityRequest, @PathVariable Integer idBiblioteca, HttpServletResponse response) {
		Biblioteca entity = service.atualiza(entityRequest, idBiblioteca);
		
		return ResponseEntity.ok( new BibliotecaResponse(entity));
	}
	
	@GetMapping("/{idBiblioteca}/operador")
	public ResponseEntity<Page<UsuarioResponse>> consultaOperadorPorFiltro(@PathVariable Integer idBiblioteca, Pageable page) {
		Page<Usuario> entities = service.consultaOperadores(idBiblioteca, page);
		
		return ResponseEntity.ok(entities.map(UsuarioResponse::new));
	}
	
	@PostMapping("/{idBiblioteca}/operador")
	public ResponseEntity<?> cadastrarOperador(@RequestBody @Valid BibliotecaOperadorCadastroRequest entityRequest, @PathVariable Integer idBiblioteca, HttpServletResponse response) {
		Usuario entity = service.cadastraOperador(idBiblioteca, entityRequest);
		
		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(entity));
	}
}
