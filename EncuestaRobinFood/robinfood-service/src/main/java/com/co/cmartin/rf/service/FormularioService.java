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
import com.co.cmartin.rf.request.FormularioRequest;
import com.co.cmartin.rf.response.FormularioResponse;

/**
 * Interfaz con metodos para gestion de Formularios
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public interface FormularioService {

	/**
	 * Metodo que lista los formularios registrados
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @return Lista de formularios
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public List<FormularioResponse> listarFormularios() throws SQLException, DatoNoEncontradoException;
	
	/**
	 * Metodo que consulta un Formulario por id
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id Identificador interno del Formulario
	 * @return Informacion del Formulario
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public FormularioResponse consultarFormulario(Long id) throws SQLException, DatoNoEncontradoException;
	
	/**
	 * Metodo que guarda informacion de un formulario
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param formularioRequest Datos de entrada de Formulario
	 * @return Datos de formulario con id interno generado
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public FormularioResponse guardarFormulario(FormularioRequest formularioRequest) throws SQLException, DatoInvalidoException, ServiceException;
	
	/**
	 * Metodo que actualiza estado de un formulario
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id Id del formulario a actualizar
	 * @throws SQLException Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	public void actualizarEstadoFormulario(Long id, boolean activo) throws SQLException, DatoInvalidoException, ServiceException;
}
