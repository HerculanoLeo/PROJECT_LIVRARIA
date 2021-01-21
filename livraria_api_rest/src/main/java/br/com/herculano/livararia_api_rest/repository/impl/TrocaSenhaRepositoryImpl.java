package br.com.herculano.livararia_api_rest.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.repository.custom.TrocaSenhaRepositoryCustom;
import br.com.herculano.utilits.repository.RepositoryUtils;

@Repository
public class TrocaSenhaRepositoryImpl implements TrocaSenhaRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public void invalidaCodigosAnteriores(String email) {
		String queryStr = "UPDATE tb_troca_senha tro SET status = " + "'E'";

		String where = RepositoryUtils.generateWhere("", "tro.email = '" + email + "'");
		where = RepositoryUtils.generateWhere(where, "tro.status = '" + "A" + "'");

		queryStr += where;

		em.createNativeQuery(queryStr).executeUpdate();
	}
}
