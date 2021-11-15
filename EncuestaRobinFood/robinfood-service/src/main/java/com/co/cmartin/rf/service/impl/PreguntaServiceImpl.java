/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.co.cmartin.rf.entity.Opcion;
import com.co.cmartin.rf.entity.Pregunta;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.repository.PreguntaRepository;
import com.co.cmartin.rf.request.PreguntaRequest;
import com.co.cmartin.rf.response.OpcionResponse;
import com.co.cmartin.rf.response.PreguntaResponse;
import com.co.cmartin.rf.service.PreguntaService;

import lombok.Getter;

/**
 * Clase implementacion de PreguntaService
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Service
public class PreguntaServiceImpl implements PreguntaService{
	
	@Autowired
	@Getter
	private PreguntaRepository preguntaRepository;

	/* (non-Javadoc)
	 * @see com.co.cmartin.rf.service.PreguntaService#listarPreguntas()
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public List<PreguntaResponse> listarPreguntas() throws SQLException, DatoNoEncontradoException {
		try {
			List<Pregunta> pregunta = getPreguntaRepository().findAll();
		
			if(!pregunta.isEmpty()) {				
				return pregunta.stream().map(this::mapeoEntidadPreguntaAPreguntaResponse)
						.collect(Collectors.toList());				
			} else {
				throw new DatoNoEncontradoException("No se encontro informacion de preguntas");
			}
		} catch (Exception e) {
			throw new SQLException("Error al consultar informacion de las preguntas", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.co.cmartin.rf.service.PreguntaService#consultarPregunta(java.lang.Long)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public PreguntaResponse consultarPregunta(Long id) throws SQLException, DatoNoEncontradoException {
		try {
			Pregunta pregunta = getPreguntaRepository().getById(id);
			
			return mapeoEntidadPreguntaAPreguntaResponse(pregunta);
		} catch (EntityNotFoundException e) {
			throw new DatoNoEncontradoException("No se encontro informacion asociada a la pregunta: " + id, e);
		} catch (Exception e) {
			throw new SQLException("Error al consultar informacion de la pregunta: " + id, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.co.cmartin.rf.service.PreguntaService#guardarPregunta(com.co.cmartin.rf.request.PreguntaRequest)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public PreguntaResponse guardarPregunta(PreguntaRequest preguntaRequest) throws SQLException, DatoInvalidoException {
		try {
			Pregunta respuesta = getPreguntaRepository().save(mapeoPreguntaRequestAEntidadPregunta(preguntaRequest));
			
			return mapeoEntidadPreguntaAPreguntaResponse(respuesta);
		} catch (DataIntegrityViolationException e) {
			throw new DatoInvalidoException("La pregunta: '" + preguntaRequest.getDescripcion() + "-" + preguntaRequest.getTipoPregunta() + "' ya se encuentra registrada", e);
		} catch (Exception e) {
			throw new SQLException("Error al registrar pregunta: " + preguntaRequest.getDescripcion(), e);
		}
	}

	/**
	 * Metodo que mapea la informacion de OpcionRequest a Opcion
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id Id de la opcion en caso tal de ya estar registrada
	 * @param opcionRequest Informacion de entrada de Opcion
	 * @return Entidad Opcion
	 */
	private Pregunta mapeoPreguntaRequestAEntidadPregunta(PreguntaRequest preguntaRequest) {
		Pregunta pregunta = new Pregunta();
		pregunta.setDescripcion(preguntaRequest.getDescripcion());
		pregunta.setTipoPregunta(preguntaRequest.getTipoPregunta());
		pregunta.setFechaUltimaModificacion(new Date());
		if(preguntaRequest.getOpciones() != null && !preguntaRequest.getOpciones().isEmpty()) {
			pregunta.setOpcionesPregunta(preguntaRequest.getOpciones()
				.stream().map(this::mapeoIdAEntidadOpcion)
				.collect(Collectors.toList()));
		}
		
		return pregunta;
	}
	
	/**
	 * Metodo que mapea la informacion de Pregunta a PreguntaResponse
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
		if(pregunta.getOpcionesPregunta() != null && !pregunta.getOpcionesPregunta().isEmpty()) {
			preguntaResponse.setOpciones(pregunta.getOpcionesPregunta()
					.stream().map(this::mapeoEntidadOpcionAOpcionResponse)
					.collect(Collectors.toList()));
		}
		
		return preguntaResponse;
	}
	
	/**
	 * Metodo que mapea la informacion de OpcionRequest a Opcion
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id Id de la opcion en caso tal de ya estar registrada
	 * @param opcionRequest Informacion de entrada de Opcion
	 * @return Entidad Opcion
	 */
	private Opcion mapeoIdAEntidadOpcion(Long id) {
		Opcion opcion = new Opcion();
		opcion.setId(id);
		
		return opcion;
	}
	
	/**
	 * Metodo que mapea la informacion de Opcion a OpcionResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param opcion Entidad Opcion
	 * @return Informacion de salida para Opcion
	 */
	private OpcionResponse mapeoEntidadOpcionAOpcionResponse(Opcion opcion) {
		OpcionResponse opcionResponse = new OpcionResponse();
		opcionResponse.setId(opcion.getId());
		opcionResponse.setDescripcion(opcion.getDescripcion());
		
		return opcionResponse;
	}
}