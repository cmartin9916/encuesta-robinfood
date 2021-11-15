/**
 * @autor Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
package com.co.cmartin.rf.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de entrada para Persona
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
@Data
public class PersonaRequest {
	@NotBlank(message = "El campo identificación es obligatorio")
	@ApiModelProperty(value = "Identificación", name = "identificacion", dataType = "String")
	private String identificacion;
	@NotBlank(message = "El campo nombres es obligatorio")
	@ApiModelProperty(value = "Nombres del empleado", name = "nombres", dataType = "String")
	private String nombres;
	@NotBlank(message = "El campo apellidos es obligatorio")
	@ApiModelProperty(value = "Apellidos", name = "apellidos", dataType = "String")
	private String apellidos;
	@NotBlank(message = "El campo correo electrónico es obligatorio")
	@ApiModelProperty(value = "Correo Electrónico", name = "correoElectronico", dataType = "String")
	private String correoElectronico;
}
