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

import br.com.herculano.livararia_api_rest.controller.request.perfil.PerfilCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.perfil.PerfilConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.perfil.PerfilUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.response.PerfilConsultaResponse;
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
	@PreAuthorize("@resourcesSecurity.isAcessoPerfis(authentication, #entityRequest)")
	public ResponseEntity<Page<PerfilConsultaResponse>> consultaPerfilPorFiltro(PerfilConsultaRequest entityRequest, Pageable page) {
		Page<Perfil> entity = service.consultaPorFiltro(entityRequest, page);

		return ResponseEntity.ok(entity.map(PerfilConsultaResponse::new));
	}

	@GetMapping("/permissoes")
	public ResponseEntity<Page<PermissaoResponse>> consultaPermissoes(Pageable page) {
		Page<Permissao> entity = permissaoService.consulta(page);

		return ResponseEntity.ok(entity.map(PermissaoResponse::new));
	}

	@GetMapping("/{idPerfil}")
	public ResponseEntity<PerfilResponse> consultaPerfilPorId(@PathVariable Integer idPerfil) {
		Perfil entity = service.consultaPorId(idPerfil);

		return ResponseEntity.ok(new PerfilResponse(entity));
	}

	@PostMapping
	public ResponseEntity<?> cadastraPerfil(@RequestBody PerfilCadastroRequest request, HttpServletResponse response) {
		Perfil entity = service.cadastra(request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new PerfilResponse(entity));
	}

	@PostMapping("/{idAdministrador}") //TODO testar quando tiver biblioteca cadastrada.
	public ResponseEntity<?> cadastraPerfilComAdministrador(@PathVariable("idAdministrador") Integer idAdministrador, @RequestBody PerfilCadastroRequest request,
			HttpServletResponse response) {
		Perfil entity = service.cadastra(idAdministrador, request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new PerfilResponse(entity));
	}

	@PutMapping("/{idPerfil}")
	public ResponseEntity<?> atualizaPerfil(@RequestBody @Validated PerfilUpdateRequest request, @PathVariable Integer idPerfil, HttpServletResponse response) {
		Perfil entity = service.atualizar(idPerfil, request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.OK).body(new PerfilResponse(entity));
	}

}
