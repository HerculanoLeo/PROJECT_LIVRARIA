package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.controller.request.biblioteca.BibliotecaConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import br.com.herculano.livararia_api_rest.repository.custom.BibliotecaRespositoryCustom;
import br.com.herculano.utilities.repository.RepositoryUtils;

@Repository
public class BibliotecaRepositoryImpl implements BibliotecaRespositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Biblioteca> consultaPorFiltro(BibliotecaConsultaRequest entityRequest, Pageable page) {
		String queryStr = "SELECT b.* FROM tb_biblioteca b "
				+ " INNER JOIN tb_usuario_adminisitrador ua ON b.id_administrador = ua.id_usuario "
				+ " INNER JOIN tb_usuario u ON ua.id_usuario = u.id ";

		String where = "";
		Map<String, Object> params = new HashMap<>();

		if (StringUtils.isNotBlank(entityRequest.getNomeBiblioteca())) {
			where = RepositoryUtils.generateWhere(where, "UPPER(b.nome) LIKE :a ");
			params.put("a", "%" + entityRequest.getNomeBiblioteca().toUpperCase() + "%");
		}

		if (StringUtils.isNotBlank(entityRequest.getNomeAdministrador())) {
			where = RepositoryUtils.generateWhere(where, "UPPER(u.nome) LIKE :b ");
			params.put("b", "%" + entityRequest.getNomeAdministrador().toUpperCase() + "%");
		}

		queryStr += where;

		queryStr += " ORDER BY b.id ASC";

		Long totalResgistros = RepositoryUtils.totalRegistros(queryStr, em, params);

		queryStr += RepositoryUtils.adicionarPaginacao(page);

		Query query = em.createNativeQuery(queryStr, Biblioteca.class);

		for (Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		List<Biblioteca> entities = query.getResultList();

		return new PageImpl<Biblioteca>(entities, page, totalResgistros);
	}

	//@Override
	// exemplo de consulta com filtro usando CriteriaQuery
	public Page<Biblioteca> aconsultaPorFiltro(BibliotecaConsultaRequest entityRequest, Pageable page) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Biblioteca> cq = cb.createQuery(Biblioteca.class);
		
		Root<Biblioteca> biblioteca = cq.from(Biblioteca.class);
		
		Join<Biblioteca, UsuarioAdministrador> administrador = biblioteca.join("administrador", JoinType.INNER);

		Map<String, JoinType> joinMap = new HashMap<>();
		joinMap.put("administrador", JoinType.INNER);

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(entityRequest.getNomeBiblioteca())) {
			predicates.add(cb.like(cb.upper(biblioteca.get("nome")), "%" + entityRequest.getNomeBiblioteca().toUpperCase() + "%"));
		}

		if (StringUtils.isNotBlank(entityRequest.getNomeAdministrador())) {
			predicates.add(cb.like(cb.upper(administrador.get("nome")), "%" + entityRequest.getNomeAdministrador().toUpperCase() + "%"));
		}

		cq.select(biblioteca).where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<Biblioteca> query = em.createQuery(cq);

		query.setFirstResult(Math.toIntExact(page.getOffset()));
		query.setMaxResults(page.getPageSize());

		List<Biblioteca> entities = query.getResultList();

		Long totalRegistros = totalRegistros(predicates);
		
		return new PageImpl<Biblioteca>(entities, page, totalRegistros);
	}

	private Long totalRegistros(List<Predicate> predicates) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Biblioteca> root = cq.from(Biblioteca.class);

		root.join("administrador", JoinType.INNER);
		
		cq.select(cb.count(root));
		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		return em.createQuery(cq).getSingleResult();
	}
	

}
