package br.com.herculano.livararia_api_rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.herculano.livararia_api_rest.controller.request.LivroCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.response.AutorResponse;
import br.com.herculano.livararia_api_rest.controller.response.LivroResponse;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.entity.Livro;
import br.com.herculano.livararia_api_rest.event.CreatedEvent;
import br.com.herculano.livararia_api_rest.service.AutorService;
import br.com.herculano.livararia_api_rest.service.LivroService;

@RestController
@RequestMapping("/livro")
public class LivroController {

	@Autowired
	private LivroService service;

	@Autowired
	private AutorService autorService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<Page<LivroResponse>> consultaLivros(Pageable page) {
		Page<Livro> entities = service.consulta(page);

		return ResponseEntity.ok(service.convertePageListaResponse(entities));
	}

	@PostMapping
	public ResponseEntity<LivroResponse> cadastrarLivro(@RequestBody @Validated LivroCadastroRequest request,
			HttpServletResponse response) {

		Livro entity = service.cadastra(request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new LivroResponse(entity));
	}

	@GetMapping("/{id}")
	public ResponseEntity<LivroResponse> consultaPorId(@PathVariable Integer id, UriComponentsBuilder uirBuilder) {
		Livro entity = service.consultaPorId(id);

		return ResponseEntity.ok(new LivroResponse(entity));
	}

	@PutMapping("/{idLivro}")
	public ResponseEntity<LivroResponse> atulizarLivro(@RequestBody @Validated LivroCadastroRequest request,
			@PathVariable Integer idLivro, HttpServletResponse response) {

		Livro entity = service.atualizar(idLivro, request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.OK).body(new LivroResponse(entity));
	}

	@GetMapping("/{idLivro}/autor")
	public ResponseEntity<List<AutorResponse>> buscaAutoresPorIdLivro(@PathVariable Integer idLivro, Pageable page,
			HttpServletResponse response) {
		Page<Autor> entities = autorService.consultaPorIdLivro(idLivro, page);

		return ResponseEntity.ok(entities.stream().map(AutorResponse::new).collect(Collectors.toList()));
	}

	@PutMapping("/{idLivro}/autor/{idAutor}")
	public ResponseEntity<LivroResponse> adicionaAutor(@RequestBody @PathVariable Integer idLivro,
			@PathVariable Integer idAutor, HttpServletResponse response) {

		Livro entity = service.adiconaAutorPorId(idLivro, idAutor);

		return ResponseEntity.status(HttpStatus.OK).body(new LivroResponse(entity));
	}

	@DeleteMapping("/{idLivro}")
	ResponseEntity<LivroResponse> deleteLivro(@PathVariable Integer idLivro, UriComponentsBuilder uriBuilder) {
		service.delete(idLivro);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{idLivro}/autor/{idAutor}")
	public ResponseEntity<LivroResponse> deleteAutor(@RequestBody @PathVariable Integer idLivro,
			@PathVariable Integer idAutor, HttpServletResponse response) {

		service.deleteAutorPorId(idLivro, idAutor);

		return ResponseEntity.ok().build();
	}

}
