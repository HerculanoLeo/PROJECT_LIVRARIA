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

import br.com.herculano.livararia_api_rest.controller.request.GrupoUsuarioCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.GrupoUsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.response.GrupoUsuarioResponse;
import br.com.herculano.livararia_api_rest.controller.response.PermissaoResponse;
import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;
import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.event.CreatedEvent;
import br.com.herculano.livararia_api_rest.service.GrupoUsuarioService;
import br.com.herculano.livararia_api_rest.service.PermissaoService;

@RestController
@RequestMapping("/grupo")
public class GrupoUsuarioController {

	@Autowired
	private GrupoUsuarioService service;

	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<Page<GrupoUsuarioResponse>> consultaGruposPorFiltro(GrupoUsuarioConsultaRequest entityRequest, Pageable page) {

		Page<GrupoUsuario> entity = service.consultaPorFiltro(entityRequest, page);

		return ResponseEntity.ok(entity.map(GrupoUsuarioResponse::new));
	}

	@GetMapping("/permissoes")
	public ResponseEntity<Page<PermissaoResponse>> consultaPermissoes(Pageable page) {
		Page<Permissao> entity = permissaoService.consulta(page);

		return ResponseEntity.ok(entity.map(PermissaoResponse::new));
	}

	@GetMapping("/{idGrupoUsuario}")
	public ResponseEntity<GrupoUsuario> consultaGrupoUsuarioPorId(@PathVariable Integer idGrupoUsuario) {
		GrupoUsuario entity = service.consultaPorId(idGrupoUsuario);

		return ResponseEntity.ok(entity);
	}

	@PostMapping
	public ResponseEntity<?> cadastraGrupo(@RequestBody GrupoUsuarioCadastroRequest request, HttpServletResponse response) {

		GrupoUsuario entity = service.cadastra(request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new GrupoUsuarioResponse(entity));
	}

	@PutMapping("/{idGrupoUsuario}")
	public ResponseEntity<?> atualizaGrupo(@RequestBody GrupoUsuarioCadastroRequest request,
			@PathVariable Integer idGrupoUsuario, HttpServletResponse response) {

		GrupoUsuario entity = service.atualizar(idGrupoUsuario, request);
		
		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.OK).body(new GrupoUsuarioResponse(entity));
	}

	@DeleteMapping("/{idGrupoUsuario}")
	public ResponseEntity<?> deletarGrupo(@PathVariable Integer idGrupoUsuario) {
		service.delete(idGrupoUsuario);

		return ResponseEntity.ok().build();
	}
}
