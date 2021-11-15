/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.exception;

/**
 * Excepcion cuando ocurre un error no controlado desde la capa de acceso a datos
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public class SQLException extends Exception {

	private static final long serialVersionUID = 1L;

	public SQLException() {
		super();
	}

	public SQLException(String message) {
		super(message);
	}
	
	public SQLException(String message, Throwable cause) {
		super(message, cause);
	}
}
