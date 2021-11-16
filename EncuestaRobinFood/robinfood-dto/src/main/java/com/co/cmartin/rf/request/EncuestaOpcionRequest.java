/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de entrada para opciones de una pregunta para una encuesta 
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class EncuestaOpcionRequest {
	@NotBlank(message = "El campo id es obligatorio")
	@ApiModelProperty(value = "id", name = "id", dataType = "Long")
	private Long id;
	@ApiModelProperty(value = "descripción", name = "descripcion", dataType = "String")
	private String descripcion;
	@NotNull(message = "El campo seleccionada es obligatorio")
	@ApiModelProperty(value = "seleccionada", name = "seleccionada", dataType = "boolean")
	private boolean seleccionada;
}
