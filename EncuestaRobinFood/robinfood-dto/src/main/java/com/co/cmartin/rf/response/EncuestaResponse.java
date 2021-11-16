/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Clase con datos de salida para Encuesta
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class EncuestaResponse {
	private Long idFormulario;
	private String titulo;
	private String descripcion;
	private List<EncuestaPreguntaResponse> preguntas;
}
