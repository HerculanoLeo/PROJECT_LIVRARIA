package br.com.herculano.livrariaREST.repository.utils;

import org.apache.commons.lang3.StringUtils;

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
	
	
	
}
