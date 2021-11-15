/**
 * @autor Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
package com.co.cmartin.rf.rest.advice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.ErrorDetails;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.exception.ServiceException;

/**
 * Controller Advice para manejo de respuesta segun excepciones
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String STATUS = "status";
	private static final String MESSAGE = "message";
	private static final String TIMESTAMP = "timestamp";

	@ExceptionHandler(value = { SQLException.class })
	protected ResponseEntity<Object> handleConflict(SQLException ex, WebRequest request) {
		logger.error("Error Interno SQLException", ex);

		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDateTime.now());
		if (ex.getCause() != null) {
			body.put(MESSAGE, ex.getMessage() + ": " + ex.getCause().getMessage());
		} else {
			body.put(MESSAGE, ex.getMessage());
		}
		body.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());

		return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = { ServiceException.class })
	protected ResponseEntity<Object> handleConflict(ServiceException ex, WebRequest request) {
		logger.error("Error Interno ServiceException", ex);

		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDateTime.now());
		if (ex.getCause() != null) {
			body.put(MESSAGE, ex.getMessage() + ": " + ex.getCause().getMessage());
		} else {
			body.put(MESSAGE, ex.getMessage());
		}
		body.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());

		return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = { DatoNoEncontradoException.class })
	protected ResponseEntity<Object> handleConflict(DatoNoEncontradoException ex, WebRequest request) {
		logger.error("Error DatoNoEncontradoException", ex);

		Map<String, Object> body = new LinkedHashMap<>();
		body.put(STATUS, HttpStatus.NO_CONTENT.value());

		return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NO_CONTENT, request);
	}
	
	@ExceptionHandler(value = { DatoInvalidoException.class })
	protected ResponseEntity<Object> handleConflict(DatoInvalidoException ex, WebRequest request) {
		logger.error("Error DatoInvalidoException", ex);

		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP, LocalDateTime.now());
		body.put(MESSAGE, ex.getMessage());
		body.put(STATUS, HttpStatus.BAD_REQUEST.value());

		return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, "Error en la validacion de campos", errorList);
        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
    }
	
}
