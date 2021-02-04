package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import br.com.herculano.livararia_api_rest.controller.request.OperadorConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.entity.UsuarioAdministrador;
import br.com.herculano.livararia_api_rest.entity.UsuarioOperador;
import br.com.herculano.livararia_api_rest.repository.custom.UsuarioOperadorRepositoryCustom;

@Repository
public class UsuarioOperadorRepositoryImpl implements UsuarioOperadorRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<UsuarioOperador> consulta(OperadorConsultaRequest entityRequest, Pageable page) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<UsuarioOperador> cq = cb.createQuery(UsuarioOperador.class);

		Root<UsuarioOperador> usuarioOperador = cq.from(UsuarioOperador.class);

		Join<UsuarioOperador, Biblioteca> biblioteca = usuarioOperador.join("biblioteca", JoinType.INNER);

		Join<Biblioteca, UsuarioAdministrador> administrador = biblioteca.join("administrador", JoinType.INNER);

		List<Predicate> predicates = new ArrayList<>();

		if (null != entityRequest.getIdBiblioteca() && entityRequest.getIdBiblioteca() > 0) {
			predicates.add(cb.equal(biblioteca.get("id"), entityRequest.getIdBiblioteca()));
		}

		if (null != entityRequest.getIdAdministrador() && entityRequest.getIdAdministrador() > 0) {
			predicates.add(cb.equal(administrador.get("id"), entityRequest.getIdAdministrador()));
		}

		if (StringUtils.isNotBlank(entityRequest.getNomeOperador())) {
			predicates.add(cb.like(cb.upper(usuarioOperador.get("nome")), "%" + entityRequest.getNomeOperador().toUpperCase() + "%"));
		}

		if (StringUtils.isNotBlank(entityRequest.getNomeBiblioteca())) {
			predicates.add(cb.like(cb.upper(biblioteca.get("nome")), "%" + entityRequest.getNomeBiblioteca().toUpperCase() + "%"));
		}

		if (StringUtils.isNotBlank(entityRequest.getDocumento())) {
			predicates.add(cb.like(cb.upper(usuarioOperador.get("documento")), "%" + entityRequest.getDocumento() + "%"));
		}

		if (StringUtils.isNotBlank(entityRequest.getEmail())) {
			predicates.add(cb.like(cb.upper(usuarioOperador.get("email")), "%" + entityRequest.getEmail().toUpperCase() + "%"));
		}

		cq.select(usuarioOperador).where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<UsuarioOperador> query = em.createQuery(cq);

		query.setFirstResult(Math.toIntExact(page.getOffset()));
		query.setMaxResults(page.getPageSize());

		List<UsuarioOperador> entities = query.getResultList();

		return new PageImpl<UsuarioOperador>(entities, page, totalRegistros(predicates));
	}

	private Long totalRegistros(List<Predicate> predicates) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<UsuarioOperador> root = cq.from(UsuarioOperador.class);

		Join<UsuarioOperador, Biblioteca> biblioteca = root.join("biblioteca", JoinType.INNER);
		biblioteca.join("administrador", JoinType.INNER);
		
		cq.select(cb.count(root));
		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		return em.createQuery(cq).getSingleResult();
	}

}
