/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service;

import java.sql.SQLException;
import java.util.List;

import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.ServiceException;
import com.co.cmartin.rf.request.PreguntaRequest;
import com.co.cmartin.rf.response.PreguntaResponse;

/**
 * Interfaz con metodos para gestion de Preguntas
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public interface PreguntaService {

	/**
	 * Metodo que lista las preguntas registradas
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @return Lista de preguntas
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public List<PreguntaResponse> listarPreguntas() throws SQLException, DatoNoEncontradoException;
	
	/**
	 * Metodo que consulta una pregunta por id
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id Identificador interno de la persona
	 * @return Informacion de la pregunta
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public PreguntaResponse consultarPregunta(Long id) throws SQLException, DatoNoEncontradoException;
	
	/**
	 * Metodo que guarda informacion de una pregunta
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param preguntaRequest Datos de entrada de Pregunta
	 * @return Datos de pregunta con id interno generado
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public PreguntaResponse guardarPregunta(PreguntaRequest preguntaRequest) throws SQLException, DatoInvalidoException, ServiceException;
	
}
