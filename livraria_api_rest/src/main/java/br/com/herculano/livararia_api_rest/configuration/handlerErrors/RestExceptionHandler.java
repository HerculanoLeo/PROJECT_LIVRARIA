package br.com.herculano.livararia_api_rest.configuration.handlerErrors;

import java.util.List;

import javax.persistence.EntityNotFoundException;

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
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.herculano.livararia_api_rest.constants.system_message.CommonMessage;
import br.com.herculano.livararia_api_rest.exception.custom.ConfirmPasswordException;
import br.com.herculano.livararia_api_rest.exception.custom.EmptyGrupoUsuarioException;
import br.com.herculano.livararia_api_rest.exception.custom.TrocaSenhaException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private CommonMessage message;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = CommonMessage.getCodigo(message.getJsonMalformed(), null);
		
		return buildResponseEntity(new ApiError(status, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError api = new ApiError(HttpStatus.BAD_REQUEST);
		api.setMessage(CommonMessage.getCodigo(message.getValidationError(), null));
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		api.addValidationErrors(fieldErrors);
		List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
		api.addValidationError(globalErrors);
		return buildResponseEntity(api);
	}

	@ExceptionHandler({ ConfirmPasswordException.class, EmptyGrupoUsuarioException.class })
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
			error = CommonMessage.getCodigo(message.getUserNotFound(), null);

		} else if (ex instanceof BadCredentialsException) {
			error = CommonMessage.getCodigo(message.getUserOrPasswordIncorrect(), null);
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
}
