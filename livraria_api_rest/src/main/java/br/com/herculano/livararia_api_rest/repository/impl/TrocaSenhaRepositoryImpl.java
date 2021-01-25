package br.com.herculano.livararia_api_rest.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.constants.CodigoRecuperaSenhaStatusEnum;
import br.com.herculano.livararia_api_rest.repository.custom.TrocaSenhaRepositoryCustom;
import br.com.herculano.utilities.repository.RepositoryUtils;

@Repository
public class TrocaSenhaRepositoryImpl implements TrocaSenhaRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public void invalidaCodigosAnteriores(String email) {
		String queryStr = "UPDATE tb_troca_senha tro SET status = :a";

		String where = RepositoryUtils.generateWhere("", "tro.email = :b");
		where = RepositoryUtils.generateWhere(where, "tro.status = :c");

		queryStr += where;

		Query query = em.createNativeQuery(queryStr);

		query.setParameter("a", CodigoRecuperaSenhaStatusEnum.EXPIRADO.getValor());
		query.setParameter("b", email);
		query.setParameter("c", CodigoRecuperaSenhaStatusEnum.ATIVO.getValor());
		
		query.executeUpdate();
	}
}
