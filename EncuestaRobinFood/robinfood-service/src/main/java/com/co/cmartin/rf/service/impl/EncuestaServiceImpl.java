/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.cmartin.rf.entity.Encuesta;
import com.co.cmartin.rf.entity.Formulario;
import com.co.cmartin.rf.entity.Opcion;
import com.co.cmartin.rf.entity.Persona;
import com.co.cmartin.rf.entity.Pregunta;
import com.co.cmartin.rf.enums.TipoPregunta;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.exception.ServiceException;
import com.co.cmartin.rf.repository.EncuestaRepository;
import com.co.cmartin.rf.repository.FormularioRepository;
import com.co.cmartin.rf.request.EncuestaOpcionRequest;
import com.co.cmartin.rf.request.EncuestaPreguntaRequest;
import com.co.cmartin.rf.request.EncuestaRequest;
import com.co.cmartin.rf.response.EncuestaOpcionResponse;
import com.co.cmartin.rf.response.EncuestaPreguntaResponse;
import com.co.cmartin.rf.response.EncuestaResponse;
import com.co.cmartin.rf.response.PersonaResponse;
import com.co.cmartin.rf.service.EncuestaService;
import com.co.cmartin.rf.service.PersonaService;
import com.co.cmartin.rf.service.RespuestaService;
import com.co.cmartin.rf.util.Utils;

import lombok.Getter;

/**
 * Clase implementacion de EncuestaService
 * 
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Service
public class EncuestaServiceImpl implements EncuestaService {

	@Autowired
	@Getter
	private EncuestaRepository encuestaRepository;

	@Autowired
	@Getter
	private FormularioRepository formularioRepository;

	@Autowired
	@Getter
	private PersonaService personaService;

	@Autowired
	@Getter
	private RespuestaService respuestaService;

	/*
	 * (non-Javadoc)
	 * @see com.co.cmartin.rf.service.EncuestaService#consultarFormulario(java.lang.Long)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public EncuestaResponse consultarFormulario(Long id) throws SQLException, DatoInvalidoException, DatoNoEncontradoException {
		try {
			Formulario formulario = getFormularioRepository().getById(id);

			if (formulario.isActivo()) {
				return mapeoEntidadFormularioAFormularioResponse(formulario);
			} else {
				throw new DatoInvalidoException("El formulario: '" + id + "' esta inactivo");
			}
		} catch (EntityNotFoundException e) {
			throw new DatoNoEncontradoException("No se encontro informacion asociada al formulario: " + id, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.co.cmartin.rf.service.EncuestaService#guardarEncuesta(com.co.cmartin.rf.request.EncuestaRequest)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public void guardarEncuesta(EncuestaRequest encuestaRequest)
			throws SQLException, DatoNoEncontradoException, DatoInvalidoException, ServiceException {
		Utils.validarListaObligatoria(encuestaRequest.getPreguntas(), "preguntas");
		validarInformacionFormulario(encuestaRequest);

		PersonaResponse persona = null;

		if (encuestaRequest.getDatosPersona() != null) {
			persona = getPersonaService().guardarPersona(encuestaRequest.getDatosPersona());
		}

		Encuesta encuesta = crearEncuesta(encuestaRequest, persona);

		getRespuestaService().guardarTodasRespuesta(encuesta, encuestaRequest);
	}

	/**
	 * Metodo que crea una encuesta
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param encuestaRequest
	 * @return Encuesta
	 * @throws SQLException
	 */
	private Encuesta crearEncuesta(EncuestaRequest encuestaRequest, PersonaResponse personaResponse) throws SQLException {
		try {
			Encuesta encuesta = mapeoEncuestaRequestAEntidadFormulario(encuestaRequest, personaResponse);

			return getEncuestaRepository().save(encuesta);
		} catch (Exception e) {
			throw new SQLException("Error al guardar informacion de la encuesta para el formulario: " + encuestaRequest.getIdFormulario(), e);
		}
	}

	private Encuesta mapeoEncuestaRequestAEntidadFormulario(EncuestaRequest encuestaRequest, PersonaResponse personaResponse) {
		Encuesta encuesta = new Encuesta();

		Formulario formulario = new Formulario();
		formulario.setId(encuestaRequest.getIdFormulario());
		encuesta.setFormulario(formulario);

		if (personaResponse != null) {
			Persona persona = new Persona();
			persona.setId(personaResponse.getId());
			encuesta.setPersona(persona);
		}

		return encuesta;
	}

	/**
	 * Metodo que mapea la informacion de Formulario a EncuestaResponse
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param formulario Entidad Pregunta
	 * @return Informacion de salida para Pregunta
	 */
	private EncuestaResponse mapeoEntidadFormularioAFormularioResponse(Formulario formulario) {
		EncuestaResponse encuestaResponse = new EncuestaResponse();
		encuestaResponse.setIdFormulario(formulario.getId());
		encuestaResponse.setTitulo(formulario.getTitulo());
		encuestaResponse.setDescripcion(formulario.getDescripcion());
		encuestaResponse.setPreguntas(
				formulario.getPreguntasFormulario().stream().map(this::mapeoEntidadPreguntaAEncuestaPreguntaResponse).collect(Collectors.toList()));

		return encuestaResponse;
	}

	/**
	 * Metodo que mapea la informacion de Pregunta a EncuestaPreguntaResponse
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param pregunta Entidad Pregunta
	 * @return Informacion de salida para Pregunta
	 */
	private EncuestaPreguntaResponse mapeoEntidadPreguntaAEncuestaPreguntaResponse(Pregunta pregunta) {
		EncuestaPreguntaResponse preguntaResponse = new EncuestaPreguntaResponse();
		preguntaResponse.setId(pregunta.getId());
		preguntaResponse.setDescripcion(pregunta.getDescripcion());
		preguntaResponse.setTipoPregunta(pregunta.getTipoPregunta());

		if (pregunta.getTipoPregunta() == TipoPregunta.ABIERTA) {
			preguntaResponse.setRespuestaAbierta("");
		} else {
			preguntaResponse.setRespuestas(
					pregunta.getOpcionesPregunta().stream().map(this::mapeoEntidadOpcionAEncuestaOpcionResponse).collect(Collectors.toList()));
		}

		return preguntaResponse;
	}

	/**
	 * Metodo que mapea la informacion de Opcion a EncuestaOpcionResponse
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param opcion Entidad Opcion
	 * @return Informacion de salida para Opcion
	 */
	private EncuestaOpcionResponse mapeoEntidadOpcionAEncuestaOpcionResponse(Opcion opcion) {
		EncuestaOpcionResponse opcionResponse = new EncuestaOpcionResponse();
		opcionResponse.setId(opcion.getId());
		opcionResponse.setDescripcion(opcion.getDescripcion());

		return opcionResponse;
	}

	/**
	 * Metodo que valida que la informacion del formulario sea correcto
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param preguntaRequest Informacion de entrada para las preguntas
	 * @throws DatoInvalidoException Ocurre si se presentan inconsistencias en la informacion
	 * @throws ServiceException      Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	private void validarInformacionFormulario(EncuestaRequest encuestaRequest) throws DatoInvalidoException {
		for (EncuestaPreguntaRequest pregunta : encuestaRequest.getPreguntas()) {
			validarInformacionPregunta(pregunta);
		}
	}

	/**
	 * Metodo que valida que la informacion de las preguntas sea correcta
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param preguntaRequest Informacion de entrada para las preguntas
	 * @throws DatoInvalidoException Ocurre si se presentan inconsistencias en la informacion
	 * @throws ServiceException      Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	private void validarInformacionPregunta(EncuestaPreguntaRequest preguntaRequest) throws DatoInvalidoException {
		if (preguntaRequest.getTipoPregunta() == TipoPregunta.ABIERTA) {
			if (preguntaRequest.getRespuestaAbierta() == null || preguntaRequest.getRespuestaAbierta().isEmpty()) {
				throw new DatoInvalidoException("La pregunta: " + preguntaRequest.getDescripcion() + ", no tiene respuesta");
			}
		} else {
			Utils.validarListaObligatoria(preguntaRequest.getRespuestas(), "respuestas de pregunta: " + preguntaRequest.getDescripcion());

			Optional<EncuestaOpcionRequest> opcion = preguntaRequest.getRespuestas().stream().filter(EncuestaOpcionRequest::isSeleccionada)
					.findFirst();
			if (!opcion.isPresent()) {
				throw new DatoInvalidoException("No se encontro respuesta seleccionada para la pregunta: " + preguntaRequest.getDescripcion());
			}
		}
	}
}
