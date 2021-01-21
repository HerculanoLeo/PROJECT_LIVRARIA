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

import br.com.herculano.livararia_api_rest.controller.request.PerfilConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.PerfilCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.response.GrupoUsuarioResponse;
import br.com.herculano.livararia_api_rest.controller.response.PerfilResponse;
import br.com.herculano.livararia_api_rest.controller.response.PermissaoResponse;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.event.CreatedEvent;
import br.com.herculano.livararia_api_rest.service.PerfilService;
import br.com.herculano.livararia_api_rest.service.PermissaoService;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

	@Autowired
	private PerfilService service;

	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<Page<PerfilResponse>> consultaGruposPorFiltro(PerfilConsultaRequest entityRequest, Pageable page) {
		Page<Perfil> entity = service.consultaPorFiltro(entityRequest, page);

		return ResponseEntity.ok(entity.map(PerfilResponse::new));
	}

	@GetMapping("/permissoes")
	public ResponseEntity<Page<PermissaoResponse>> consultaPermissoes(Pageable page) {
		Page<Permissao> entity = permissaoService.consulta(page);

		return ResponseEntity.ok(entity.map(PermissaoResponse::new));
	}

	@GetMapping("/{idPerfil}")
	public ResponseEntity<Perfil> consultaPerfilPorId(@PathVariable Integer idPerfil) {
		Perfil entity = service.consultaPorId(idPerfil);

		return ResponseEntity.ok(entity);
	}

	@PostMapping
	public ResponseEntity<?> cadastraGrupo(@RequestBody PerfilCadastroRequest request, HttpServletResponse response) {

		Perfil entity = service.cadastra(request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new GrupoUsuarioResponse(entity));
	}

	@PutMapping("/{idPerfil}")
	public ResponseEntity<?> atualizaPerfil(@RequestBody PerfilCadastroRequest request,
			@PathVariable Integer idPerfil, HttpServletResponse response) {

		Perfil entity = service.atualizar(idPerfil, request);
		
		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.OK).body(new GrupoUsuarioResponse(entity));
	}

	@DeleteMapping("/{idPerfil}")
	public ResponseEntity<?> deletarPerfil(@PathVariable Integer idPerfil) {
		service.delete(idPerfil);

		return ResponseEntity.ok().build();
	}
}
