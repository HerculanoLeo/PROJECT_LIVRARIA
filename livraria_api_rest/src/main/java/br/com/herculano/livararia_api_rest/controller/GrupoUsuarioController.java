package br.com.herculano.livararia_api_rest.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.herculano.livararia_api_rest.controller.request.GrupoUsuarioRequest;
import br.com.herculano.livararia_api_rest.controller.request.PermissaoRequest;
import br.com.herculano.livararia_api_rest.controller.response.GrupoUsuarioResponse;
import br.com.herculano.livararia_api_rest.controller.response.PermissaoResponse;
import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;
import br.com.herculano.livararia_api_rest.entity.Permissao;
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
	public ResponseEntity<Page<GrupoUsuarioResponse>> consultaGruposPorFiltro(
			@RequestParam(name = "nomeGrupo", required = false) String nomeGrupo,
			@RequestParam(name = "nomePermissoes", required = false) List<String> nomePermissoes, Pageable page) {

		GrupoUsuario filterEntity = new GrupoUsuario();
		filterEntity.setNome(nomeGrupo);

		if (nomePermissoes != null) {
			filterEntity.setPermissoes(nomePermissoes.stream().map(Permissao::new).collect(Collectors.toList()));
		}

		Page<GrupoUsuario> entity = service.consultaPorFiltro(filterEntity, page);

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
	public ResponseEntity<?> cadastraGrupo(@RequestBody GrupoUsuarioRequest request, UriComponentsBuilder uriBuilder) {

		List<Permissao> permissoes = new ArrayList<Permissao>();

		validaGrupoUsuario(request, permissoes);

		GrupoUsuario entity = new GrupoUsuario();

		entity.setNome(request.getNome());
		entity.setPermissoes(permissoes);

		service.save(entity);

		URI uri = uriBuilder.path("/grupos/{id}").buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{idGrupoUsuario}")
	public ResponseEntity<?> atualizaGrupo(@RequestBody GrupoUsuarioRequest request,
			@PathVariable Integer idGrupoUsuario, UriComponentsBuilder uriBuilder) {

		List<Permissao> permissoes = new ArrayList<Permissao>();

		validaGrupoUsuario(request, permissoes, idGrupoUsuario);

		GrupoUsuario entity = new GrupoUsuario();

		entity.setId(idGrupoUsuario);
		entity.setNome(request.getNome());
		entity.setPermissoes(permissoes);

		service.save(entity);

		URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{idGrupoUsuario}")
	public ResponseEntity<?> deletarGrupo(@PathVariable Integer idGrupoUsuario) {

		service.delete(idGrupoUsuario);

		return ResponseEntity.ok().build();
	}

	private void validaGrupoUsuario(GrupoUsuarioRequest request, List<Permissao> permissoes, Integer id) {

		GrupoUsuario entity = service.consultaPorId(id);

		validaGrupoUsuario(request, permissoes);
	}

	private void validaGrupoUsuario(GrupoUsuarioRequest request, List<Permissao> permissoes) {

		if (request.getPermissoes() != null) {
			for (PermissaoRequest permissao : request.getPermissoes()) {
				Optional<Permissao> optional = permissaoService.consultaPorCodigo(permissao.getAuthority());

				if (!optional.isPresent()) {
					throw new EntityNotFoundException(permissao.getAuthority() + " not exist.");
				}

				permissoes.add(optional.get());
			}
		}
	}
}