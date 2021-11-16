/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de salida para Formulario
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class FormularioResponse {
	@ApiModelProperty(value = "Id Interno", name = "id", dataType = "Long")
	private Long id;
	@ApiModelProperty(value = "titulo", name = "descripcion", dataType = "String")
	private String titulo;
	@ApiModelProperty(value = "descripción", name = "descripcion", dataType = "String")
	private String descripcion;
	@ApiModelProperty(value = "activo", name = "activo", dataType = "boolean")
	private boolean activo;
	@ApiModelProperty(value = "preguntas", name = "preguntas", dataType = "")
	private List<PreguntaResponse> preguntas;
}
