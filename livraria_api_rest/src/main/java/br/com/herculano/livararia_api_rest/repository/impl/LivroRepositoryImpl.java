package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.controller.request.livro.LivroConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Livro;
import br.com.herculano.livararia_api_rest.repository.custom.LivroRepositoryCustom;
import br.com.herculano.utilities.repository.RepositoryUtils;

@Repository
public class LivroRepositoryImpl implements LivroRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override 
	public Page<Livro> consulta(LivroConsultaRequest entityRequest, Pageable page) {
		String queryStr = "SELECT DISTINCT(l.*) "
				+ " FROM tb_livro l "
				+ " INNER JOIN tb_biblioteca b ON b.id = l.id_biblioteca "
				+ " INNER JOIN tb_livro_autor l_a ON l_a.id_livro = l.id "
				+ " INNER JOIN tb_autor a ON a.id = l_a.id_autor ";
				
		String where = "";
		Map<String, Object> params = new HashMap<>();

		if (null != entityRequest.getId() && entityRequest.getId() > 0) {
			where = RepositoryUtils.generateWhere(where, "l.id = :a ");
			params.put("a", entityRequest.getId());
		}
		
		if (StringUtils.isNotBlank(entityRequest.getTitulo())) {
			where = RepositoryUtils.generateWhere(where, "UPPER(l.titulo) LIKE :b ");
			params.put("b", "%" + entityRequest.getTitulo().toUpperCase() + "%");
		}
		
		if (null != entityRequest.getISBN()) {
			where = RepositoryUtils.generateWhere(where, "l.isbn = :c ");
			params.put("c", entityRequest.getISBN());
		}
		
		if (null != entityRequest.getIdBiblioteca() && entityRequest.getIdBiblioteca() > 0) {
			where = RepositoryUtils.generateWhere(where, "l.id_biblioteca = :d ");
			params.put("d", entityRequest.getIdBiblioteca());
		}
		
		if (null != entityRequest.getDataLancamentoInicio()) {
			where = RepositoryUtils.generateWhere(where, "l.dt_lancamento >= :e ");
			params.put("e", entityRequest.getDataLancamentoInicio());
		}
		
		if (null != entityRequest.getDataLancamentoFim()) {
			where = RepositoryUtils.generateWhere(where, "l.dt_lancamento <= :f ");
			params.put("f", entityRequest.getDataLancamentoFim());
		}
		
		if (StringUtils.isNotBlank(entityRequest.getNomeBiblioteca())) {
			where = RepositoryUtils.generateWhere(where, "UPPER(b.nome) LIKE :g ");
			params.put("g", "%" + entityRequest.getNomeBiblioteca().toUpperCase() + "%");
		}
		
		if (StringUtils.isNotBlank(entityRequest.getNomeAutor())) {
			where = RepositoryUtils.generateWhere(where, "UPPER(a.nome) LIKE :h ");
			params.put("h", "%" + entityRequest.getNomeBiblioteca().toUpperCase() + "%");
		}

		queryStr += where;

		queryStr += " ORDER BY l.id ASC";

		Long totalResgistros = RepositoryUtils.totalRegistros(queryStr, em, params);

		queryStr += RepositoryUtils.adicionarPaginacao(page);

		Query query = em.createNativeQuery(queryStr, Livro.class);

		for (Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		
		List<Livro> entities = query.getResultList();

		return new PageImpl<Livro>(entities, page, totalResgistros);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Livro> consultaPorIdAutor(Integer idAutor) {
		String queryStr = "SELECT liv.* FROM tb_livro liv "
				+ "INNER JOIN tb_livro_autor liv_aut ON liv_aut.id_livro = liv.id ";

		String where = "";

		where = "WHERE liv_aut.id_autor = '" + idAutor + "'";

		queryStr += where;

		Query query = em.createNativeQuery(queryStr, Livro.class);

		return query.getResultList();
	}

}