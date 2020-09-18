package br.com.herculano.livararia_api_rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.herculano.livararia_api_rest.controller.request.AutorRequest;
import br.com.herculano.livararia_api_rest.controller.response.AutorResponse;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.event.CreatedEvent;
import br.com.herculano.livararia_api_rest.service.AutorService;

@RestController
@RequestMapping("/autor")
public class AutorController {

	@Autowired
	private AutorService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<Page<AutorResponse>> consultaAutores(Pageable page) {
		Page<Autor> entities = service.consulta(page);

		ResponseEntity<Page<AutorResponse>> response = ResponseEntity.ok(entities.map(autor -> {
			AutorResponse entity = new AutorResponse(autor);

			return entity;
		}));

		return response;
	}

	@GetMapping("/{id}")
	public ResponseEntity<AutorResponse> consultaPorId(@PathVariable Integer id) {
		Autor entity = service.consultaPorId(id);

		return ResponseEntity.ok(new AutorResponse(entity));
	}

	@PostMapping
	public ResponseEntity<AutorResponse> cadastrarAutor(@RequestBody AutorRequest entityRequest,
			HttpServletResponse response) {
		Autor entity = new Autor(entityRequest);

		service.save(entity);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new AutorResponse(entity));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AutorResponse> atualizarAutor(@PathVariable Integer id,
			@RequestBody AutorRequest entityRequest, HttpServletResponse response) {

		AutorResponse entity = new AutorResponse(service.autilizaAutor(id, entityRequest));

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AutorResponse> deleteAutor(@PathVariable Integer id) {

		service.delete(id);
		
		return ResponseEntity.ok().build();
	}
}
