package br.com.herculano.livararia_api_rest.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import br.com.herculano.livararia_api_rest.controller.request.LivroRequest;
import br.com.herculano.livararia_api_rest.controller.response.AutorResponse;
import br.com.herculano.livararia_api_rest.controller.response.LivroResponse;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.entity.Livro;
import br.com.herculano.livararia_api_rest.service.AutorService;
import br.com.herculano.livararia_api_rest.service.LivroService;

@RestController
@RequestMapping("/livro")
public class LivroController {

	@Autowired
	private LivroService service;

	@Autowired
	private AutorService autorService;

	@GetMapping
	@PreAuthorize("hasAnyRole('CONSULTA_LIVROS')")
	public ResponseEntity<Page<LivroResponse>> consultaLivros(Pageable page) {
		Page<Livro> entities = service.consulta(page);

		return ResponseEntity.ok(service.convertePageListaDTO(entities));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('CADASTRAR_LIVRO')")
	public ResponseEntity<LivroResponse> cadastrarLivro(@RequestBody @Validated LivroRequest request,
			UriComponentsBuilder uriBuilder) {

		List<Autor> autores = new ArrayList<Autor>();

		validaLivro(request, autores);

		Livro entity = new Livro(request);

		if (!autores.isEmpty()) {
			entity.setAutores(autores);
		}

		service.save(entity);

		URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('CONSULTA_LIVRO_POR_ID')")
	public ResponseEntity<LivroResponse> consultaPorId(@PathVariable Integer id, UriComponentsBuilder uirBuilder) {
		Optional<Livro> optional = service.consultaPorId(id);

		if (optional.isPresent()) {
			LivroResponse livroDTO = new LivroResponse(optional.get());

			return ResponseEntity.ok(livroDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{idLivro}")
	@PreAuthorize("hasAnyRole('ATUALIZAR_LIVRO')")
	public ResponseEntity<LivroResponse> atulizarLivro(@RequestBody @Validated LivroRequest request,
			@PathVariable Integer idLivro, UriComponentsBuilder uriBuilder) {

		List<Autor> autores = new ArrayList<Autor>();

		validaLivro(request, idLivro, autores);

		Livro entity = new Livro(request);

		entity.setId(idLivro);

		if (!autores.isEmpty()) {
			entity.setAutores(autores);
		}

		service.save(entity);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/{idLivro}/autor")
	@PreAuthorize("hasAnyRole('CONSULTA_AUTORES_POR_ID_LIVRO')")
	public ResponseEntity<List<AutorResponse>> buscaAutoresPorIdLivro(@PathVariable Integer idLivro,
			UriComponentsBuilder uriBuilder) {
		List<Autor> entities = autorService.consultaPorIdLivro(idLivro);

		return ResponseEntity.ok(entities.stream().map(AutorResponse::new).collect(Collectors.toList()));
	}

	@PutMapping("/{idLivro}/autor/{idAutor}")
	@PreAuthorize("hasAnyRole('LIVRO_ADICIONAR_AUTOR')")
	public ResponseEntity<LivroResponse> adicionaAutor(@RequestBody @PathVariable Integer idLivro,
			@PathVariable Integer idAutor, UriComponentsBuilder uriBuilder) {
		Optional<Livro> optional = service.consultaPorId(idLivro);

		if (optional.isPresent()) {
			Livro entity = optional.get();

			Optional<Autor> optionalAutor = autorService.consultaPorId(idAutor);

			if (optionalAutor.isPresent()) {
				entity.getAutores().add(optionalAutor.get());
			} else {
				throw new EntityNotFoundException("idAutor: " + idAutor + " not exist.");
			}

			service.save(entity);

			return ResponseEntity.ok().build();
		} else {
			throw new EntityNotFoundException("idLivro: " + idLivro + " not exist.");
		}

	}

	@DeleteMapping("/{idLivro}")
	@PreAuthorize("hasAnyRole('DELETE_LIVRO')")
	ResponseEntity<LivroResponse> deleteLivro(@PathVariable Integer idLivro, UriComponentsBuilder uriBuilder) {
		Optional<Livro> optional = service.consultaPorId(idLivro);
		
		if (optional.isPresent()) {
			service.delete(optional.get());
			
			return ResponseEntity.ok().build();
		} else {
			throw new EntityNotFoundException("idLivro: " + idLivro + " not exist.");
		}
	}

	@DeleteMapping("/{idLivro}/autor/{idAutor}")
	@PreAuthorize("hasAnyRole('LIVRO_DELETE_AUTOR')")
	public ResponseEntity<LivroResponse> deleteAutor(@RequestBody @PathVariable Integer idLivro,
			@PathVariable Integer idAutor, UriComponentsBuilder uriBuilder) {

		Optional<Livro> optional = service.consultaPorId(idLivro);

		if (optional.isPresent()) {
			Livro entity = optional.get();

			service.removeAutorPorid(entity.getId(), idAutor);

			return ResponseEntity.ok().build();
		} else {

			throw new EntityNotFoundException("idLivro: " + idLivro + " not exist.");
		}
	}

	private void validaLivro(LivroRequest request, List<Autor> autores) {

		if (!request.getIdsAutor().isEmpty()) {
			for (Integer id : request.getIdsAutor()) {

				Optional<Autor> optionalAutor = autorService.consultaPorId(id);

				if (!optionalAutor.isPresent()) {
					throw new EntityNotFoundException("idAutor: " + id + " not exist.");
				}

				autores.add(optionalAutor.get());
			}
		}
	}

	private void validaLivro(LivroRequest request, Integer idLivro, List<Autor> autores) {

		Optional<Livro> optionalLivro = service.consultaPorId(idLivro);

		if (!optionalLivro.isPresent()) {
			throw new EntityNotFoundException("idLivro: " + idLivro + " not exist.");
		}

		validaLivro(request, autores);
	}
}
