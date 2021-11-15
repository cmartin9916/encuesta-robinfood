/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.rest.administracion;

import java.sql.SQLException;
import java.util.List;

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
import com.co.cmartin.rf.exception.ServiceException;
import com.co.cmartin.rf.request.PreguntaRequest;
import com.co.cmartin.rf.response.PreguntaResponse;
import com.co.cmartin.rf.service.PreguntaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;

/**
 * Clase RestController con servicios rest para gestion de preguntas
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@RestController
@Api(tags = "Pregunta")
public class PreguntaRestController {

	@Autowired
	@Getter
	private PreguntaService preguntaService;
	
	@ApiOperation(value = "Servicio que lista la informacion de las preguntas", notes = "Este servicio consulta la información de todas las preguntas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. Se encontro información", response = PreguntaResponse.class),
			@ApiResponse(code = 204, message = "No Content. No se encontro información"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping(path = "/pregunta", produces = "application/json")
	public List<PreguntaResponse> listarPreguntas() throws ServiceException, SQLException, DatoNoEncontradoException {

		return getPreguntaService().listarPreguntas();
	}
	
	@ApiOperation(value = "Servicio que consulta información de una pregunta por id", notes = "Este servicio consulta la información de una pregunta por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. Se encontro información", response = PreguntaResponse.class),
			@ApiResponse(code = 204, message = "No Content. No se encontro información asociada al id enviado"),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping(path = "/pregunta/{id}", produces = "application/json")
	public PreguntaResponse consultarPregunta(@PathVariable Long id) throws ServiceException, SQLException, DatoNoEncontradoException {

		return getPreguntaService().consultarPregunta(id);
	}
	
	@ApiOperation(value = "Servicio que guarda informacion de una pregunta", notes = "Este servicio recibe la informacion de la pregunta en el cuerpo del servicio")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. Se guardo la informacion correctamente", response = PreguntaResponse.class),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PostMapping(path = "/pregunta", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PreguntaResponse> guardarPregunta(@Valid @RequestBody PreguntaRequest preguntaRequest)
			throws ServiceException, SQLException, DatoInvalidoException {

		return new ResponseEntity<>(getPreguntaService().guardarPregunta(preguntaRequest), HttpStatus.CREATED);
	}
	
}
