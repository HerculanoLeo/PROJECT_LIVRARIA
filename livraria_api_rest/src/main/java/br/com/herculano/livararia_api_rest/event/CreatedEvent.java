package br.com.herculano.livararia_api_rest.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class CreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = -3566611462006321083L;

	private HttpServletResponse response;
	
	private String id;
	
	public CreatedEvent(Object source, HttpServletResponse response, String id) {
		super(source);
		this.response = response;
		this.id = id;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getId() {
		return id;
	}
}
