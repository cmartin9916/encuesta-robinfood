/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.rest.encuesta;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.exception.ServiceException;
import com.co.cmartin.rf.request.EncuestaRequest;
import com.co.cmartin.rf.response.EncuestaResponse;
import com.co.cmartin.rf.response.FormularioResponse;
import com.co.cmartin.rf.service.EncuestaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;

/**
 * Clase RestController con servicios rest para gestion de formularios
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@RestController
@Api(tags = "Encuesta")
public class EncuestaRestController {
	
	@Autowired
	@Getter
	private EncuestaService encuestaService;

	@ApiOperation(value = "Servicio que consulta información de un formulario por id para realizar encuesta", notes = "Este servicio consulta la información de un formulario por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. Se encontro información", response = FormularioResponse.class),
			@ApiResponse(code = 204, message = "No Content. No se encontro información asociada al id enviado"),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping(path = "/encuesta/{id}", produces = "application/json")
	public EncuestaResponse consultarFormulario(@PathVariable Long id) throws ServiceException, SQLException, DatoNoEncontradoException, DatoInvalidoException {

		return getEncuestaService().consultarFormulario(id);
	}
	
	@ApiOperation(value = "Servicio que guarda las respuestas dadas a una encuesta", notes = "Este servicio recibe la informacion de la encuesta en el cuerpo del servicio")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. Se guardo la encuesta correctamente", response = FormularioResponse.class),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PostMapping(path = "/encuesta", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Void> guardarFormulario(@Valid @RequestBody EncuestaRequest encuestaRequest)
			throws ServiceException, SQLException, DatoInvalidoException, DatoNoEncontradoException {

		getEncuestaService().guardarEncuesta(encuestaRequest);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
