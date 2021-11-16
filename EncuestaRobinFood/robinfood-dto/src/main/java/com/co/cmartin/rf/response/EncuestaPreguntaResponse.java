/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.response;

import java.util.List;

import com.co.cmartin.rf.enums.TipoPregunta;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Clase con datos de salida para Preguntas de una encuesta
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class EncuestaPreguntaResponse {
	private Long id;
	private String descripcion;
	private TipoPregunta tipoPregunta;
	private String respuestaAbierta;
	private List<EncuestaOpcionResponse> respuestas;
}
