/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.co.cmartin.rf.entity.Formulario;
import com.co.cmartin.rf.entity.Pregunta;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.exception.ServiceException;
import com.co.cmartin.rf.repository.FormularioRepository;
import com.co.cmartin.rf.request.FormularioRequest;
import com.co.cmartin.rf.response.FormularioResponse;
import com.co.cmartin.rf.response.PreguntaResponse;
import com.co.cmartin.rf.service.FormularioService;
import com.co.cmartin.rf.service.PreguntaService;
import com.co.cmartin.rf.util.Utils;

import lombok.Getter;

/**
 * Clase implementacion de FormularioService
 * 
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Service
public class FormularioServiceImpl implements FormularioService {

	@Autowired
	@Getter
	private FormularioRepository formularioRepository;

	@Autowired
	@Getter
	private PreguntaService preguntaService;

	/*
	 * (non-Javadoc)
	 * @see com.co.cmartin.rf.service.FormularioService#listarFormularios()
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public List<FormularioResponse> listarFormularios() throws SQLException, DatoNoEncontradoException {
		try {
			List<Formulario> formulario = getFormularioRepository().findAll();

			if (!formulario.isEmpty()) {
				return formulario.stream().map(this::mapeoEntidadFormularioAFormularioResponse).collect(Collectors.toList());
			} else {
				throw new DatoNoEncontradoException("No se encontro informacion de formularios");
			}
		} catch (Exception e) {
			throw new SQLException("Error al consultar informacion de las formularios", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.co.cmartin.rf.service.FormularioService#consultarFormulario(java.lang.Long)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public FormularioResponse consultarFormulario(Long id) throws SQLException, DatoNoEncontradoException {
		try {
			Formulario formulario = getFormularioRepository().getById(id);

			return mapeoEntidadFormularioAFormularioResponse(formulario);
		} catch (EntityNotFoundException e) {
			throw new DatoNoEncontradoException("No se encontro informacion asociada al formulario: " + id, e);
		} catch (Exception e) {
			throw new SQLException("Error al consultar informacion de la formulario: " + id, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.co.cmartin.rf.service.FormularioService#guardarFormulario(com.co.cmartin.rf.request.FormularioRequest)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public FormularioResponse guardarFormulario(FormularioRequest formularioRequest) throws SQLException, DatoInvalidoException, ServiceException {
		validarInformacionFormulario(formularioRequest);

		try {
			Formulario formulario = buscarFormularioPorTituloDescripcion(formularioRequest.getTitulo(), formularioRequest.getDescripcion());
			Formulario respuesta = getFormularioRepository()
					.save(mapeoFormularioRequestAEntidadFormulario(formulario == null ? null : formulario.getId(), formularioRequest));

			return mapeoEntidadFormularioAFormularioResponse(respuesta);
		} catch (DataIntegrityViolationException e) {
			throw new DatoInvalidoException("El formulario: '" + formularioRequest.getTitulo() + "' ya se encuentra registrado", e);
		} catch (Exception e) {
			throw new SQLException("Error al registrar el formulario: " + formularioRequest.getTitulo(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.co.cmartin.rf.service.FormularioService#actualizarEstadoFormulario(java.lang.Long, boolean)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public void actualizarEstadoFormulario(Long id, boolean activo) throws SQLException, DatoInvalidoException, ServiceException {
		int registrosAfectados = getFormularioRepository().actualizarEstadoFormulario(id, activo);

		if (registrosAfectados <= 0) {
			throw new DatoInvalidoException("El formulario: '" + id + "' no existe");
		}
	}

	/**
	 * Metodo que busca informacion de formulario por titulo y descripcion
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param titulo      titulo
	 * @param descripcion Descripcion
	 * @return Informacion del formulario
	 * @throws SQLException Ocurre si se presenta una excepcion no controlado al momento de acceder a la capa de datos
	 */
	private Formulario buscarFormularioPorTituloDescripcion(String titulo, String descripcion) {
		return getFormularioRepository().buscarFormularioPorTituloDescripcion(titulo, descripcion);
	}

	/**
	 * Metodo que valida que la informacion del formulario este correcto
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param formularioRequest Informacion de entrada para las preguntas
	 * @throws DatoInvalidoException Ocurre si se presentan inconsistencias en la informacion
	 * @throws SQLException          Ocurre si se presenta un error inesperado en la capa de acceso a datos
	 */
	private void validarInformacionFormulario(FormularioRequest formularioRequest) throws DatoInvalidoException, SQLException {
		Utils.validarListaObligatoria(formularioRequest.getPreguntas(), "preguntas");

		for (Long pregunta : formularioRequest.getPreguntas()) {
			try {
				getPreguntaService().consultarPregunta(pregunta);
			} catch (DatoNoEncontradoException e) {
				throw new DatoInvalidoException("La pregunta: '" + pregunta + "' no existe");
			}
		}
	}

	/**
	 * Metodo que mapea la informacion de FormularioRequest a Formulario
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id            Id del formulario en caso tal de ya estar registrada
	 * @param opcionRequest Informacion de entrada de Formulario
	 * @return Entidad Formulario
	 */
	private Formulario mapeoFormularioRequestAEntidadFormulario(Long id, FormularioRequest formularioRequest) {
		Formulario formulario = new Formulario();
		if (id != null) {
			formulario.setId(id);
		}
		formulario.setTitulo(formularioRequest.getTitulo());
		formulario.setDescripcion(formularioRequest.getDescripcion());
		formulario.setActivo(formularioRequest.isActivo());
		formulario.setFechaUltimaModificacion(new Date());
		if (formularioRequest.getPreguntas() != null && !formularioRequest.getPreguntas().isEmpty()) {
			formulario.setPreguntasFormulario(
					formularioRequest.getPreguntas().stream().map(this::mapeoIdAEntidadPregunta).collect(Collectors.toList()));
		}

		return formulario;
	}

	/**
	 * Metodo que mapea la informacion de Pregunta a PreguntaResponse
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param formulario Entidad Pregunta
	 * @return Informacion de salida para Pregunta
	 */
	private FormularioResponse mapeoEntidadFormularioAFormularioResponse(Formulario formulario) {
		FormularioResponse formularioResponse = new FormularioResponse();
		formularioResponse.setId(formulario.getId());
		formularioResponse.setTitulo(formulario.getTitulo());
		formularioResponse.setDescripcion(formulario.getDescripcion());
		formularioResponse.setActivo(formulario.isActivo());
		if (formulario.getPreguntasFormulario() != null && !formulario.getPreguntasFormulario().isEmpty()) {
			formularioResponse.setPreguntas(
					formulario.getPreguntasFormulario().stream().map(this::mapeoEntidadPreguntaAPreguntaResponse).collect(Collectors.toList()));
		}

		return formularioResponse;
	}

	/**
	 * Metodo que mapea la informacion de Id a Pregunta
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id            Id de la opcion en caso tal de ya estar registrada
	 * @param opcionRequest Informacion de entrada de Opcion
	 * @return Entidad Opcion
	 */
	private Pregunta mapeoIdAEntidadPregunta(Long id) {
		Pregunta pregunta = new Pregunta();
		pregunta.setId(id);

		return pregunta;
	}

	/**
	 * Metodo que mapea la informacion de Pregunta a PreguntaResponse
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param pregunta Entidad Pregunta
	 * @return Informacion de salida para Pregunta
	 */
	private PreguntaResponse mapeoEntidadPreguntaAPreguntaResponse(Pregunta pregunta) {
		PreguntaResponse preguntaResponse = new PreguntaResponse();
		preguntaResponse.setId(pregunta.getId());
		preguntaResponse.setDescripcion(pregunta.getDescripcion());
		preguntaResponse.setTipoPregunta(pregunta.getTipoPregunta());

		return preguntaResponse;
	}

}
