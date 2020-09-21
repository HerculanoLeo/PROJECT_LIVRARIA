package br.com.herculano.livararia_api_rest.constants.system_message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class MessageTemplate {
	
	private static MessageSource messageSource;
	
	@Autowired
	private void setMessageSource(MessageSource messageSource) {
		MessageTemplate.messageSource = messageSource;
	}
	
	public static String getCodigo(String code, Object[] args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}
	
	public abstract String getNotFound();
}
