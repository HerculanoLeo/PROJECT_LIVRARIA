package br.com.herculano.livararia_api_rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.LivroMessage;
import br.com.herculano.livararia_api_rest.controller.request.LivroCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.response.LivroResponse;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.entity.Livro;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.LivroRepository;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Service
public class LivroService extends ServiceTemplate<Livro, LivroRepository, LivroMessage> {

	@Autowired
	private AutorService autorService;
	

	@Autowired
	public LivroService(LivroRepository repository, LivroMessage message) {
		super(repository, message);
	}

	public Livro cadastra(LivroCadastroRequest request) {
		List<Autor> autores = new ArrayList<Autor>();

		validaLivro(request, autores);

		Livro entity = new Livro(request);

		if (!autores.isEmpty()) {
			entity.setAutores(autores);
		}

		super.save(entity);

		return entity;
	}

	public Livro atualizar(Integer idLivro, LivroCadastroRequest request) {
		Livro entity = super.consultaPorId(idLivro);

		List<Autor> autores = new ArrayList<Autor>();
		validaLivro(request, autores);

		if (!autores.isEmpty()) {
			entity.setAutores(autores);
		}

		entity.setISBN(request.getIsbn());
		entity.setTitulo(request.getTitulo());
		entity.setDataLancamento(request.getDataLancamento());

		super.save(entity);

		return entity;
	}

	public void delete(Integer id) {
		Livro entity = super.consultaPorId(id);
		
		super.delete(entity);
	}

	public List<Livro> consulta() {
		return this.getRepository().findAll();
	}

	public List<Livro> consultaPorIdAutor(Integer idAutor) {
		return getRepository().consultaPorIdAutor(idAutor);
	}

	public List<LivroResponse> converteListaDTO(List<Livro> entities) {
		return entities.stream().map(LivroResponse::new).collect(Collectors.toList());
	}

	public Page<LivroResponse> convertePageListaResponse(Page<Livro> entities) {
		return entities.map(LivroResponse::new);
	}

	public Livro adiconaAutorPorId(Integer idLivro, Integer idAutor) {
		Livro entity = super.consultaPorId(idLivro);

		Autor autor = autorService.consultaPorId(idAutor);
		
		entity.getAutores().add(autor);

		save(entity);

		return entity;
	}
	
	public void deleteAutorPorId(Integer idLivro, Integer idAutor) {
		Livro entity = super.consultaPorId(idLivro);
		
		this.getRepository().removeAutorPorId(entity.getId(), idAutor);
	}

	private void validaLivro(LivroCadastroRequest request, List<Autor> autores) {
		if (null != request.getIdsAutor() && !request.getIdsAutor().isEmpty()) {
			for (Integer id : request.getIdsAutor()) {
				Autor entity = autorService.consultaPorId(id);

				autores.add(entity);
			}
		}
	}

}
