/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de entrada para Formulario
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class FormularioRequest {
	@NotBlank(message = "El campo titulo es obligatorio")
	@ApiModelProperty(value = "titulo", name = "descripcion", dataType = "String")
	private String titulo;
	@NotBlank(message = "El campo descripción es obligatorio")
	@ApiModelProperty(value = "descripción", name = "descripcion", dataType = "String")
	private String descripcion;
	@NotNull(message = "El campo activo es obligatorio")
	@ApiModelProperty(value = "activo", name = "activo", dataType = "boolean")
	private boolean activo;
	@ApiModelProperty(value = "preguntas", name = "preguntas", dataType = "")
	private List<Long> preguntas;
}
