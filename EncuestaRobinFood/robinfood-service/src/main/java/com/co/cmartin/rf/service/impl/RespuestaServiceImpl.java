/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.cmartin.rf.entity.Encuesta;
import com.co.cmartin.rf.entity.Opcion;
import com.co.cmartin.rf.entity.Pregunta;
import com.co.cmartin.rf.entity.Respuesta;
import com.co.cmartin.rf.enums.TipoPregunta;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.repository.RespuestaRepository;
import com.co.cmartin.rf.request.EncuestaOpcionRequest;
import com.co.cmartin.rf.request.EncuestaPreguntaRequest;
import com.co.cmartin.rf.request.EncuestaRequest;
import com.co.cmartin.rf.service.RespuestaService;

import lombok.Getter;

/**
 * Clase implementacion de RespuestaService
 * 
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	@Getter
	private RespuestaRepository respuestaRepository;

	/*
	 * (non-Javadoc)
	 * @see com.co.cmartin.rf.service.RespuestaService#guardarRespuesta(com.co.cmartin.rf.request.EncuestaPreguntaRequest)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public void guardarTodasRespuesta(Encuesta encuesta, EncuestaRequest encuestaRequest) throws SQLException, DatoInvalidoException {

		List<Respuesta> respuestas = new ArrayList<>();

		for (EncuestaPreguntaRequest preguntaRequest : encuestaRequest.getPreguntas()) {

			respuestas.add(mapeoEncuestaPreguntaRequestAEntidadRespuesta(encuesta, preguntaRequest));
		}

		getRespuestaRepository().saveAll(respuestas);
	}

	/**
	 * Metodo que mapea la informacion de EncuestaPreguntaRequest a Pregunta
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id              Id de la pregunta en caso tal de ya estar registrada
	 * @param preguntaRequest Informacion de entrada de Pregunta
	 * @return Entidad Pregunta
	 * @throws DatoInvalidoException
	 */
	private Respuesta mapeoEncuestaPreguntaRequestAEntidadRespuesta(Encuesta encuesta, EncuestaPreguntaRequest respuestaRequest)
			throws DatoInvalidoException {
		Pregunta pregunta = new Pregunta();
		pregunta.setId(respuestaRequest.getId());

		Respuesta respuesta = new Respuesta();
		respuesta.setEncuesta(encuesta);
		respuesta.setPregunta(pregunta);

		if (respuestaRequest.getTipoPregunta() == TipoPregunta.ABIERTA) {
			respuesta.setRespuestaAbierta(respuestaRequest.getRespuestaAbierta());
		} else {
			Optional<EncuestaOpcionRequest> opcion = respuestaRequest.getRespuestas().stream().filter(EncuestaOpcionRequest::isSeleccionada)
					.findFirst();
			if (opcion.isPresent()) {
				respuesta.setRespuestaCerrada(mapeoEncuestaOpcionRequestAOpcion(opcion.get()));
			} else {
				throw new DatoInvalidoException("No se encontro respuesta seleccionada para la pregunta: " + respuestaRequest.getDescripcion());
			}
		}

		return respuesta;
	}

	/**
	 * Metodo que mapea la informacion de EncuestaOpcionRequest a Opcion
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param encuestaOpcionRequest Informacion de entrada de Opcion
	 * @return Entidad Opcion
	 */
	private Opcion mapeoEncuestaOpcionRequestAOpcion(EncuestaOpcionRequest encuestaOpcionRequest) {
		Opcion opcion = new Opcion();
		opcion.setId(encuestaOpcionRequest.getId());
		opcion.setDescripcion(encuestaOpcionRequest.getDescripcion());

		return opcion;
	}

}
