package br.com.herculano.livararia_api_rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.herculano.livararia_api_rest.controller.request.livro.LivroCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.livro.LivroConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.livro.LivroUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.response.AutorResponse;
import br.com.herculano.livararia_api_rest.controller.response.LivroResponse;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.entity.Livro;
import br.com.herculano.livararia_api_rest.event.CreatedEvent;
import br.com.herculano.livararia_api_rest.service.LivroService;

@RestController
@RequestMapping("/livro")
public class LivroController {

	@Autowired
	private LivroService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<Page<LivroResponse>> consultaLivros(LivroConsultaRequest entityRequest, Pageable page) {
		Page<Livro> entities = service.consulta(entityRequest, page);

		return ResponseEntity.ok(entities.map(LivroResponse::new));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<LivroResponse> consultaPorId(@PathVariable Integer id, UriComponentsBuilder uirBuilder) {
		Livro entity = service.consultaPorId(id);
		
		return ResponseEntity.ok(new LivroResponse(entity));
	}

	@PostMapping
	@PreAuthorize("@resourcesSecurity.isCadastroLivro(authentication, #entityRequest)")
	public ResponseEntity<LivroResponse> cadastrarLivro(@RequestBody @Validated LivroCadastroRequest entityRequest, HttpServletResponse response) {
		Livro entity = service.cadastra(entityRequest);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new LivroResponse(entity));
	}

	@PutMapping("/{idLivro}")
	public ResponseEntity<LivroResponse> atulizarLivro(@PathVariable("idLivro") Integer idLivro, @RequestBody @Validated LivroUpdateRequest entityRequest) {
		Livro entity = service.atualizar(idLivro, entityRequest);

		return ResponseEntity.ok(new LivroResponse(entity));
	}

	@GetMapping("/{idLivro}/autor")
	public ResponseEntity<Page<AutorResponse>> buscaAutoresPorIdLivro(@PathVariable Integer idLivro, Pageable page) {
		Page<Autor> entities = service.consultaPorIdLivro(idLivro, page);

		return ResponseEntity.ok(entities.map(AutorResponse::new));
	}

}
