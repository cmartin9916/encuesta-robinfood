/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service;

import com.co.cmartin.rf.entity.Encuesta;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.request.EncuestaRequest;

/**
 * Interfaz con metodos para gestion de respuestas
 * 
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public interface RespuestaService {

	/**
	 * Metodo que guarda informacion de una opcion
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param personaRequest Datos de entrada de opcion
	 * @return Datos de opcion con id interno generado
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public void guardarTodasRespuesta(Encuesta encuesta, EncuestaRequest encuestaRequest) throws SQLException, DatoInvalidoException;
}
