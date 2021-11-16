/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service;

import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.exception.ServiceException;
import com.co.cmartin.rf.request.EncuestaRequest;
import com.co.cmartin.rf.response.EncuestaResponse;

/**
 * Interfaz con metodos para gestion de Formularios
 * 
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public interface EncuestaService {
	/**
	 * Metodo que consulta un Formulario por id
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id Identificador interno del Formulario
	 * @return Informacion para registrar Encuesta
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public EncuestaResponse consultarFormulario(Long id) throws SQLException, DatoInvalidoException, DatoNoEncontradoException;

	/**
	 * Metodo que guarda las encuestas realizadas
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param encuestaRequest Informacion de entrada para guardar la encuesta
	 * @return Informacion de la encuesta realizada
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public void guardarEncuesta(EncuestaRequest encuestaRequest) throws SQLException, DatoNoEncontradoException, DatoInvalidoException, ServiceException;
}
