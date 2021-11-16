/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.co.cmartin.rf.enums.TipoPregunta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de entrada para Preguntas de una Encuesta
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class EncuestaPreguntaRequest {

	@NotBlank(message = "El campo id es obligatorio")
	@ApiModelProperty(value = "id", name = "id", dataType = "Long")
	private Long id;
	@ApiModelProperty(value = "descripción", name = "descripcion", dataType = "String")
	private String descripcion;
	@ApiModelProperty(value = "tipo de pregunta", name = "tipoPregunta", dataType = "String")
	private TipoPregunta tipoPregunta;
	@ApiModelProperty(value = "respuesta a pregunta abierta", name = "respuestaAbierta", dataType = "String")
	private String respuestaAbierta;
	@ApiModelProperty(value = "listado con opciones de respuesta", name = "respuestas", dataType = "")
	private List<EncuestaOpcionRequest> respuestas;
}
