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

import br.com.herculano.livararia_api_rest.controller.request.perfil.PerfilConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Perfil;
import br.com.herculano.livararia_api_rest.repository.custom.PerfilRepositoryCustom;
import br.com.herculano.utilities.repository.RepositoryUtils;

@Repository
public class PerfilRepositoryImpl implements PerfilRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public Page<Perfil> consultaPorFiltro(PerfilConsultaRequest entityRequest, Pageable page) {
		String queryStr = "SELECT DISTINCT(p.*) FROM tb_perfil p"
				+ " LEFT JOIN tb_perfil_permissao p_p ON p_p.id_perfil = p.id";

		String where = "";
		Map<String, Object> params = new HashMap<>();

		if (StringUtils.isNotBlank(entityRequest.getNome())) {
			where = RepositoryUtils.generateWhere(where, " UPPER(p.nome) LIKE :a");
			params.put("a","%" + entityRequest.getNome().toUpperCase() + "%");
		}

		if (StringUtils.isNotBlank(entityRequest.getNomePermissao())) {
			where = RepositoryUtils.generateWhere(where, "UPPER(p_p.id_permissao) LIKE :b");
			params.put("b", "%" + entityRequest.getNomePermissao().toUpperCase() + "%");
		}
		
		if (null != entityRequest.getIdAdministrador() && entityRequest.getIdAdministrador() > 0) {
			where = RepositoryUtils.generateWhere(where, "p.id_administrador = :d");
			params.put("d", entityRequest.getIdAdministrador());
		}

		queryStr += where;
		
		queryStr += " ORDER BY p.id ASC";

		Long totalResgistros = RepositoryUtils.totalRegistros(queryStr, em);
		queryStr += RepositoryUtils.adicionarPaginacao(page);

		Query query = em.createNativeQuery(queryStr, Perfil.class);
		
		for (Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		
		List<Perfil> entities = query.getResultList();

		return new PageImpl<>(entities, page, totalResgistros);
	}
}
