/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de salida para Opcion
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class OpcionResponse {
	@ApiModelProperty(value = "Id Interno", name = "id", dataType = "Long")
	private Long id;
	@ApiModelProperty(value = "descripción", name = "descripcion", dataType = "String")
	private String descripcion;
}
