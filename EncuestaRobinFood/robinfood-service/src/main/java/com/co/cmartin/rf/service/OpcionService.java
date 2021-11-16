/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service;

import java.util.List;

import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.request.OpcionRequest;
import com.co.cmartin.rf.response.OpcionResponse;

/**
 * Interfaz con metodos para gestion de Opciones de Pregunta
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public interface OpcionService {

	/**
	 * Metodo que lista las opciones registradas
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @return Lista de opciones
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public List<OpcionResponse> listarOpciones() throws SQLException, DatoNoEncontradoException;
	
	/**
	 * Metodo que consulta una opcion por id
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id Identificador interno de la opcion
	 * @return Informacion de la opcion
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public OpcionResponse consultarOpcion(Long id) throws SQLException, DatoNoEncontradoException;
	
	/**
	 * Metodo que guarda informacion de una opcion
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param personaRequest Datos de entrada de opcion
	 * @return Datos de opcion con id interno generado
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public OpcionResponse guardarOpcion(OpcionRequest personaRequest) throws SQLException, DatoInvalidoException;
}
