package br.com.herculano.livararia_api_rest.repository.utils;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

public class RepositoryUtils {
	
	public static String generateWhere(String where) {
		if(StringUtils.isBlank(where)) {
			where += " WHERE ";
		} else {
			where += " AND ";
		}
		return where;
	}
	
	public static String generateWhere(String where, String clause) {
		String result = generateWhere(where);
		
		result += clause;
		
		return result;
	}
	
	public static String adicionarPaginacao(Pageable page) {
		String queryStr = "";
		
		int actualPage = page.getPageNumber();
		int pageSize = page.getPageSize();
		int fistResult = actualPage * pageSize;
		
		queryStr += " LIMIT " + pageSize;
		queryStr += " OFFSET " + fistResult;
		
		return queryStr;
	}
	
	public static Long totalRegistros(String query, EntityManager em) {
		String queryStr = "SEECT COUNT(count.*) FROM (" + query + ") AS count";
		
		return Long.valueOf(em.createNativeQuery(queryStr).getFirstResult());
	}
	
}
