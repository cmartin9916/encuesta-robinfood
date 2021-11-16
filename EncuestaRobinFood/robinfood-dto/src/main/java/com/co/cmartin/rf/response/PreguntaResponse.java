/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.response;

import java.util.List;

import com.co.cmartin.rf.enums.TipoPregunta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Clase con datos de salida para Pregunta
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Data
public class PreguntaResponse {
	@ApiModelProperty(value = "Id Interno", name = "id", dataType = "Long")
	private Long id;
	@ApiModelProperty(value = "descripción", name = "descripcion", dataType = "String")
	private String descripcion;
	@ApiModelProperty(value = "tipo de pregunta", name = "tipoPregunta", dataType = "String")
	private TipoPregunta tipoPregunta;
	@ApiModelProperty(value = "opciones", name = "opciones", dataType = "")
	private List<OpcionResponse> opciones;
}
