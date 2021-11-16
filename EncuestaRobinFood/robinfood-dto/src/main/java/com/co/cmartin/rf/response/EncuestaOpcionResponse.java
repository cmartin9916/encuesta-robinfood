/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.response;

import lombok.Data;

/**
 * Clase con datos de salida para las opciones de respuesta de una encuesta
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class EncuestaOpcionResponse {
	private Long id;
	private String descripcion;
	private boolean seleccionada;
}
