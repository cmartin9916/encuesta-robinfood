/**
 * @autor Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
package com.co.cmartin.rf.service;

import java.util.List;

import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.request.PersonaRequest;
import com.co.cmartin.rf.response.PersonaResponse;

/**
 * Interfaz con metodos para gestion de Personas
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
public interface PersonaService {

	/**
	 * Metodo que lista las personas registradas
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @return Lista de personas
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public List<PersonaResponse> listarPersonas() throws SQLException, DatoNoEncontradoException;
	
	/**
	 * Metodo que consulta una persona por id
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id Identificador interno de la persona
	 * @return Informacion de la persona
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public PersonaResponse consultarPersona(Long id) throws SQLException, DatoNoEncontradoException;
	
	/**
	 * Metodo que guarda informacion de una persona
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param personaRequest Datos de entrada de Persona
	 * @return Datos de persona con id interno generado
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public PersonaResponse guardarPersona(PersonaRequest personaRequest) throws SQLException;
	
	
	
}
