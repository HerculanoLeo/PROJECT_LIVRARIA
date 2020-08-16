package br.com.herculano.livrariaREST.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.herculano.livrariaREST.repository.custom.UsuarioRespositoryCustom;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRespositoryCustom {

	@PersistenceContext
	private EntityManager em;

}
