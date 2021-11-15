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
import com.co.cmartin.rf.request.OpcionRequest;
import com.co.cmartin.rf.response.OpcionResponse;
import com.co.cmartin.rf.service.OpcionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;

/**
 * Clase RestController con servicios rest para gestion de opciones de preguntas
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@RestController
@Api(tags = "Opción Pregunta")
public class OpcionRestController {

	@Autowired
	@Getter
	private OpcionService opcionService;
	
	@ApiOperation(value = "Servicio que lista la informacion de las opciones", notes = "Este servicio consulta la información de todas las opciones")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. Se encontro información", response = OpcionResponse.class),
			@ApiResponse(code = 204, message = "No Content. No se encontro información"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping(path = "/opcion", produces = "application/json")
	public List<OpcionResponse> listarOpciones() throws ServiceException, SQLException, DatoNoEncontradoException {

		return getOpcionService().listarOpciones();
	}
	
	@ApiOperation(value = "Servicio que consulta información de una opción por id", notes = "Este servicio consulta la información de una opción por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. Se encontro información", response = OpcionResponse.class),
			@ApiResponse(code = 204, message = "No Content. No se encontro información asociada al id enviado"),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping(path = "/opcion/{id}", produces = "application/json")
	public OpcionResponse consultarOpcion(@PathVariable Long id) throws ServiceException, SQLException, DatoNoEncontradoException {

		return getOpcionService().consultarOpcion(id);
	}
	
	@ApiOperation(value = "Servicio que guarda informacion de una opción", notes = "Este servicio recibe la informacion de la opción en el cuerpo del servicio")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. Se guardo la informacion correctamente", response = OpcionResponse.class),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PostMapping(path = "/opcion", consumes = "application/json", produces = "application/json")
	public ResponseEntity<OpcionResponse> guardarOpcion(@Valid @RequestBody OpcionRequest opcionRequest)
			throws ServiceException, SQLException, DatoInvalidoException {

		return new ResponseEntity<>(getOpcionService().guardarOpcion(opcionRequest), HttpStatus.CREATED);
	}
}
