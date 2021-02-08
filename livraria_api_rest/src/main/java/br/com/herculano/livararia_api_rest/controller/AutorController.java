package br.com.herculano.livararia_api_rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.herculano.livararia_api_rest.controller.request.autor.AutorCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.autor.AutorConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.autor.AutorUpdateRequest;
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
	public ResponseEntity<Page<AutorResponse>> consultaAutores(AutorConsultaRequest entityRequest, Pageable page) {
		Page<Autor> entities = service.consultaPorFiltro(entityRequest, page);

		return ResponseEntity.ok(entities.map(AutorResponse::new));
	}

	@GetMapping("/{id}")
	public ResponseEntity<AutorResponse> consultaPorId(@PathVariable Integer id) {
		Autor entity = service.consultaPorId(id);

		return ResponseEntity.ok(new AutorResponse(entity));
	}

	@PostMapping
	@PreAuthorize("@resourcesSecurity.isCadastroAutor(authentication, #entityRequest)")
	public ResponseEntity<AutorResponse> cadastrarAutor(@RequestBody AutorCadastroRequest entityRequest, HttpServletResponse response) {
		Autor entity = service.cadastro(entityRequest);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new AutorResponse(entity));
	}

	@PutMapping("/{idAutor}")
	public ResponseEntity<AutorResponse> atualizarAutor(@PathVariable("idAutor") Integer idAutor, @RequestBody AutorUpdateRequest entityRequest) {
		Autor autor = service.autilizaAutor(idAutor, entityRequest);

		return ResponseEntity.ok(new AutorResponse(autor));
	}

	// TODO Criar status inativo
//	@DeleteMapping("/{id}")
//	public ResponseEntity<AutorResponse> deleteAutor(@PathVariable Integer id) {
//
//		service.delete(id);
//		
//		return ResponseEntity.ok().build();
//	}
}
