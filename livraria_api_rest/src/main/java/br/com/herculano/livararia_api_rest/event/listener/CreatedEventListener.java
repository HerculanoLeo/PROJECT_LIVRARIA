package br.com.herculano.livararia_api_rest.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.herculano.livararia_api_rest.event.CreatedEvent;

@Component
public class CreatedEventListener implements ApplicationListener<CreatedEvent> {

	@Override
	public void onApplicationEvent(CreatedEvent event) {
		addLocationHeader(event.getResponse(), event.getId());
	}

	private void addLocationHeader(HttpServletResponse response, String id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();

		response.addHeader(HttpHeaders.LOCATION, uri.toASCIIString());
	}

}
