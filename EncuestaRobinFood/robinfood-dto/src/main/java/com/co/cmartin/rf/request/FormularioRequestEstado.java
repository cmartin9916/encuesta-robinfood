/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de entrada para actualizar estado de Formulario
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class FormularioRequestEstado {
	@ApiModelProperty(value = "Id Interno", name = "id", dataType = "Long")
	private Long id;
	@NotNull(message = "El campo activo es obligatorio")
	@ApiModelProperty(value = "activo", name = "activo", dataType = "boolean")
	private boolean activo;
}
