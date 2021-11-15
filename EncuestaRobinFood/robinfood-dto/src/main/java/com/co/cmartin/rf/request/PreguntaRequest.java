/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.co.cmartin.rf.enums.TipoPregunta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de entrada para Pregunta
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class PreguntaRequest {

	@NotBlank(message = "El campo descripción es obligatorio")
	@ApiModelProperty(value = "descripción", name = "descripcion", dataType = "String")
	private String descripcion;
	@NotNull(message = "El campo tipo de pregunta es obligatorio")
	@ApiModelProperty(value = "tipo de pregunta", name = "tipoPregunta", dataType = "String")
	private TipoPregunta tipoPregunta;
	@ApiModelProperty(value = "opciones", name = "opciones", dataType = "")
	private List<Long> opciones;
}
