package com.co.cmartin.rf.exception;

/**
 * Excepcion cuando ocurre un error no controlado en la capa de logica de negocio
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
public class ServiceException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
