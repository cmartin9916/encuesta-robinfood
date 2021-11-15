/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de entrada para Opcion
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class OpcionRequest {

	@NotBlank(message = "El campo descripción es obligatorio")
	@ApiModelProperty(value = "descripción", name = "descripcion", dataType = "String")
	private String descripcion;
}
