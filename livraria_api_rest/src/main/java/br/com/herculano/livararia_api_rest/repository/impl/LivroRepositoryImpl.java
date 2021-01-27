package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.entity.Livro;
import br.com.herculano.livararia_api_rest.repository.custom.LivroRepositoryCustom;
import br.com.herculano.utilities.repository.RepositoryUtils;

@Repository
public class LivroRepositoryImpl implements LivroRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

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

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public void removeAutorPorId(Integer idLivro, Integer idAutor) {
		String queryStr = "DELETE FROM tb_livro_autor liv_aut";

		String where = RepositoryUtils.generateWhere("", "liv_aut.id_autor = '" + idAutor + "'");

		where = RepositoryUtils.generateWhere(where, "liv_aut.id_livro = '" + idLivro + "'");

		queryStr += where;

		em.createNativeQuery(queryStr).executeUpdate();
	}

}