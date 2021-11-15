/**
 * @autor Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
package com.co.cmartin.rf.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de salida para Persona
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
@Data
public class PersonaResponse {
	@ApiModelProperty(value = "Id Interno", name = "id", dataType = "Long")
	private Long id;
	@ApiModelProperty(value = "Identificación", name = "identificacion", dataType = "String")
	private String identificacion;
	@ApiModelProperty(value = "Nombres", name = "nombres", dataType = "String")
	private String nombres;
	@ApiModelProperty(value = "Apellidos", name = "apellidos", dataType = "String")
	private String apellidos;
	@ApiModelProperty(value = "Correo Electrónico", name = "correoElectronico", dataType = "String")
	private String correoElectronico;
}
