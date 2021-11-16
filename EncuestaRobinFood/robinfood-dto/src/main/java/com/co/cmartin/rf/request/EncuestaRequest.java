/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de entrada para Encuesta
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class EncuestaRequest {

	@NotNull(message = "El campo id formulario es obligatorio")
	@ApiModelProperty(value = "id formulario", name = "idFormulario", dataType = "Long")
	private Long idFormulario;
	@ApiModelProperty(value = "titulo", name = "titulo", dataType = "String")
	private String titulo;
	@ApiModelProperty(value = "descripción", name = "descripcion", dataType = "String")
	private String descripcion;
	@ApiModelProperty(value = "Datos Persona", name = "datosPersona", dataType = "String")
	private PersonaRequest datosPersona;
	@NotNull(message = "El campo preguntas es obligatorio")
	@ApiModelProperty(value = "preguntas", name = "preguntas", dataType = "")
	private List<EncuestaPreguntaRequest> preguntas;
}
