/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.exception;

/**
 * Excepcion cuando no se encuentra informacion
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public class DatoNoEncontradoException extends Exception {

	private static final long serialVersionUID = 1L;

	public DatoNoEncontradoException() {
		super();
	}

	public DatoNoEncontradoException(String message) {
		super(message);
	}

	public DatoNoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}
}
