package br.com.herculano.livararia_api_rest.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.herculano.livararia_api_rest.controller.request.AutorRequest;
import br.com.herculano.livararia_api_rest.controller.response.AutorResponse;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.service.AutorService;

@RestController
@RequestMapping("/autor")
public class AutorController {

	@Autowired
	private AutorService service;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('CONSULTA_AUTORES')")
	public ResponseEntity<Page<AutorResponse>> consultaAutores(Pageable page) {
		Page<Autor> listaAutor = service.consulta(page);

		ResponseEntity<Page<AutorResponse>> response = ResponseEntity.ok(listaAutor.map(autor -> {
			AutorResponse entity = new AutorResponse(autor);
			
			return entity;
		}));
		
		return response;
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('CONSULTA_AUTOR_POR_ID')")
	public ResponseEntity<AutorResponse> consultaPorId(@PathVariable Integer id) {
		Optional<Autor> optional = service.consultaPorId(id);

		if (optional.isPresent()) {
			AutorResponse entity = new AutorResponse(optional.get());
			
			return ResponseEntity.ok(entity);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('CADASTRAR_AUTOR')")
	public ResponseEntity<AutorResponse> cadastrarAutor(@RequestBody AutorRequest entityForm, UriComponentsBuilder uriBuilder) {
		Autor entity = new Autor(entityForm);

		service.save(entity);

		URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ATUALIZAR_AUTOR')")
	public ResponseEntity<AutorResponse> atualizarAutor(@PathVariable Integer id, @RequestBody AutorRequest entityForm,
			UriComponentsBuilder uriBuilder) {
		Optional<Autor> optional = service.consultaPorId(id);

		if (optional.isPresent()) {
			Autor entity = optional.get();

			entity.setNome(entityForm.getNome());
			entity.setDataNascimento(entityForm.getDataNascimento());
			entity.setDataFalecimento(entityForm.getDataFalecimento());
			
			service.save(entity);

			URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();

			return ResponseEntity.created(uri).build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('DELETE_AUTOR')")
	public ResponseEntity<AutorResponse> deleteAutor(@PathVariable Integer id) {
		Optional<Autor> optional = service.consultaPorId(id);
		
		if (optional.isPresent()) {
			service.delete(optional.get());

			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
