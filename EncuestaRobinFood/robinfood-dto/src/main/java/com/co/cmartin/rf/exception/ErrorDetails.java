/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * Clase con detalles de error '@Valid'
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class ErrorDetails {
	private HttpStatus status;
	private String message;
	private List<String> errors;

	public ErrorDetails(HttpStatus status, String message, List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public ErrorDetails(HttpStatus status, String message, String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}
}
