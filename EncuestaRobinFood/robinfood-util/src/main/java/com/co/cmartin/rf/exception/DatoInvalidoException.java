/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.exception;

/**
 * Excepcion cuando un dato es invalido
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public class DatoInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public DatoInvalidoException() {
		super();
	}

	public DatoInvalidoException(String message) {
		super(message);
	}

	public DatoInvalidoException(String message, Throwable cause) {
		super(message, cause);
	}
}
