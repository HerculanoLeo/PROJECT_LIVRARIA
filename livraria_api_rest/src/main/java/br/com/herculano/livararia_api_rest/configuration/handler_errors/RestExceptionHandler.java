package br.com.herculano.livararia_api_rest.configuration.handler_errors;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.herculano.utilities.api_error.ApiError;
import br.com.herculano.utilities.exceptions.ConfirmPasswordException;
import br.com.herculano.utilities.exceptions.DadosInvalidosException;
import br.com.herculano.utilities.exceptions.EmptyPerfilException;
import br.com.herculano.utilities.exceptions.ResourceForbiddenException;
import br.com.herculano.utilities.exceptions.TrocaSenhaException;
import br.com.herculano.utilities.templates.CommonMessageTemplate;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler implements AccessDeniedHandler, AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7425831800631693017L;

	@Autowired
	private CommonMessageTemplate message;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = CommonMessageTemplate.getCodigo(message.getJsonMalformed(), null);

		return buildResponseEntity(new ApiError(status, error, ex));
	}

	@Override // TODO corrigir a internacionalizacao da ApiError
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError api = new ApiError(HttpStatus.BAD_REQUEST);

		api.setMessage(CommonMessageTemplate.getCodigo(message.getValidationError(), null));
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		api.addValidationErrors(fieldErrors);

		List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
		api.addValidationError(globalErrors);

		return buildResponseEntity(api);
	}

	@ExceptionHandler({ ConfirmPasswordException.class, EmptyPerfilException.class, DadosInvalidosException.class })
	protected ResponseEntity<Object> handleConfirmPasswordException(Exception ex) {
		ApiError api = new ApiError(HttpStatus.BAD_REQUEST);
		
		api.setMessage(ex.getMessage());
		
		return buildResponseEntity(api);
	}

	@ExceptionHandler({ TrocaSenhaException.class, AccessDeniedException.class })
	protected ResponseEntity<Object> handleTrocaSenhaException(Exception ex) {
		ApiError api = new ApiError(HttpStatus.UNAUTHORIZED);
		
		api.setMessage(ex.getMessage());
		
		return buildResponseEntity(api);
	}

	@ExceptionHandler({ ResourceForbiddenException.class })
	protected ResponseEntity<Object> handleResourceForbiddenException(Exception ex) {
		ApiError api = new ApiError(HttpStatus.FORBIDDEN);
		
		api.setMessage(ex.getMessage());
		
		return buildResponseEntity(api);
	}

	@ExceptionHandler({ EntityNotFoundException.class, JpaObjectRetrievalFailureException.class })
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		ApiError api = new ApiError(HttpStatus.NOT_FOUND);
		
		api.setMessage(ex.getMessage());
		
		return buildResponseEntity(api);
	}

	@ExceptionHandler({ InternalAuthenticationServiceException.class, BadCredentialsException.class })
	protected ResponseEntity<Object> handleLoginValidationErrors(Exception ex) {
		String error = "";

		if (ex instanceof InternalAuthenticationServiceException) {
			error = CommonMessageTemplate.getCodigo(message.getUserNotFound(), null);

		} else if (ex instanceof BadCredentialsException) {
			error = CommonMessageTemplate.getCodigo(message.getUserOrPasswordIncorrect(), null);
		}

		return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, error, ex));
	}

	@ExceptionHandler({ Exception.class })
	protected ResponseEntity<Object> handleException(Exception ex) {
		ex.printStackTrace();

		ApiError api = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
		api.setMessage(ex.getMessage());

		return buildResponseEntity(api);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError api) {
		return new ResponseEntity<>(api, api.getStatus());
	}

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex) throws IOException, ServletException {
		ApiError api = new ApiError(HttpStatus.FORBIDDEN);
		api.setMessage(ex.getLocalizedMessage());
				
		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		om.registerModule(new JavaTimeModule());

		ObjectWriter ow = om.writer();
		String json = ow.writeValueAsString(api);

		res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		res.addHeader("Content-Type", "application/json");
		res.getWriter().write(json);
		res.getWriter().flush();
		res.getWriter().close();
	}

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException, ServletException {
		ApiError api = new ApiError(HttpStatus.UNAUTHORIZED);
		api.setMessage(ex.getLocalizedMessage());
				
		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		om.registerModule(new JavaTimeModule());

		ObjectWriter ow = om.writer();
		String json = ow.writeValueAsString(api);

		res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		res.addHeader("Content-Type", "application/json");
		res.getWriter().write(json);
		res.getWriter().flush();
		res.getWriter().close();
	}
}
