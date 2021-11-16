/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.rest.administracion;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.exception.ServiceException;
import com.co.cmartin.rf.request.FormularioRequest;
import com.co.cmartin.rf.request.FormularioRequestEstado;
import com.co.cmartin.rf.response.FormularioResponse;
import com.co.cmartin.rf.service.FormularioService;

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
@Api(tags = "Formulario")
public class FormularioRestController {

	@Autowired
	@Getter
	private FormularioService formularioService;
	
	@ApiOperation(value = "Servicio que lista la informacion de los formularios", notes = "Este servicio consulta la información de todos los formularios")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. Se encontro información", response = FormularioResponse.class),
			@ApiResponse(code = 204, message = "No Content. No se encontro información"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping(path = "/formulario", produces = "application/json")
	public List<FormularioResponse> listarFormularios() throws ServiceException, SQLException, DatoNoEncontradoException {

		return getFormularioService().listarFormularios();
	}
	
	@ApiOperation(value = "Servicio que consulta información de un formulario por id", notes = "Este servicio consulta la información de un formulario por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. Se encontro información", response = FormularioResponse.class),
			@ApiResponse(code = 204, message = "No Content. No se encontro información asociada al id enviado"),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@GetMapping(path = "/formulario/{id}", produces = "application/json")
	public FormularioResponse consultarFormulario(@PathVariable Long id) throws ServiceException, SQLException, DatoNoEncontradoException {

		return getFormularioService().consultarFormulario(id);
	}
	
	@ApiOperation(value = "Servicio que guarda informacion de un formulario", notes = "Este servicio recibe la informacion del formulario en el cuerpo del servicio")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created. Se guardo la informacion correctamente", response = FormularioResponse.class),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PostMapping(path = "/formulario", consumes = "application/json", produces = "application/json")
	public ResponseEntity<FormularioResponse> guardarFormulario(@Valid @RequestBody FormularioRequest formularioRequest)
			throws ServiceException, SQLException, DatoInvalidoException {

		return new ResponseEntity<>(getFormularioService().guardarFormulario(formularioRequest), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Servicio que actualiza el estado de un formulario", notes = "Este servicio recibe el id del formulario en el path de la url y el estado en el cuerpo del servicio")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok. Se guardo la informacion correctamente"),
			@ApiResponse(code = 400, message = "Bad Request. Ocurre cuando la información de entrada no es correcta, es decir, información faltante o enviada en un formato incorrecto"),
			@ApiResponse(code = 500, message = "Error inesperado del sistema") })
	@PatchMapping(path = "/formulario/{id}", consumes = "application/json", produces = "application/json")
	public void actualizarEstadoFormulario(@PathVariable Long id, @Valid @RequestBody FormularioRequestEstado formularioRequest)
			throws ServiceException, SQLException, DatoInvalidoException {

		getFormularioService().actualizarEstadoFormulario(id, formularioRequest.isActivo());
	}
	
	
	
	
}
