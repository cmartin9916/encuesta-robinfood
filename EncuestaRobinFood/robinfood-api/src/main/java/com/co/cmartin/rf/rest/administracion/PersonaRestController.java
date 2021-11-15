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

import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.ServiceException;
import com.co.cmartin.rf.request.PersonaRequest;
import com.co.cmartin.rf.response.PersonaResponse;
import com.co.cmartin.rf.service.PersonaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;

/**
 * Clase RestController con servicios rest para gestion de personas
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@RestController
@Api(tags = "Persona")
public class PersonaRestController {

	@Autowired
	@Getter
	private PersonaService personaService;
	
	@ApiOperation(value = "Servicio que lista la informacion de las personas", notes = "Este servicio consulta la información de todas las personas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. Se encontro información", response = PersonaResponse.class),
			@ApiResponse(code = 204, message = "No Content. No se encontro información"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping(path = "/persona", produces = "application/json")
	public List<PersonaResponse> listarPersonas() throws ServiceException, SQLException, DatoNoEncontradoException {

		return getPersonaService().listarPersonas();
	}
	
	@ApiOperation(value = "Servicio que consulta información de una persona por id", notes = "Este servicio consulta la información de una persona por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. Se encontro información", response = PersonaResponse.class),
			@ApiResponse(code = 204, message = "No Content. No se encontro información asociada al id enviado"),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping(path = "/persona/{id}", produces = "application/json")
	public PersonaResponse listarPersonas(@PathVariable Long id) throws ServiceException, SQLException, DatoNoEncontradoException {

		return getPersonaService().consultarPersona(id);
	}
	
	@ApiOperation(value = "Servicio que guarda información de una persona", notes = "Este servicio recibe la informacion de la persona en el cuerpo del servicio")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. Se guardo la informacion correctamente", response = PersonaResponse.class),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PostMapping(path = "/persona", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PersonaResponse> guardarPersona(@Valid @RequestBody PersonaRequest personaRequest)
			throws ServiceException, SQLException {

		return new ResponseEntity<>(getPersonaService().guardarPersona(personaRequest), HttpStatus.CREATED);
	}
}
